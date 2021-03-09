package builder.vanilla;

import builder.base.IBuilder;
import config.Config;
import version.base.IVersionConfig;
import version.base.nodes.IVersionArguments;
import version.base.nodes.IVersionLibrary;
import version.base.nodes.IVersionRule;
import version.base.nodes.OsDescription;
import version.vanilla.standartconfig.VersionArguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Builder implements IBuilder {

    private final Config config;
    public final IVersionConfig versionConfig;

    public Builder(Config config, IVersionConfig versionConfig)
    {
        this.config = config;
        this.versionConfig = versionConfig;
    }

    @Override
    public String buildRun() throws Exception {
        String command = "java ";

        IVersionArguments gameArguments = versionConfig.getArguments("game");
        IVersionArguments jvmArguments = versionConfig.getArguments("jvm");

        List<IVersionRule> jvmRules = resolveRules(jvmArguments.getRuleArguments());

        for (IVersionRule item : jvmRules)
            command += item.getValues();

        for(String value : jvmArguments.getStringArguments())
            command+=value+" ";

        for(String value: gameArguments.getStringArguments())
            command+=value+" ";


        return setProperties(command, config, versionConfig);
    }

    private List<IVersionRule> resolveRules(IVersionRule[] rules)
    {
        return Arrays.stream(rules).filter(
                x-> (x.getOsDescription().name == null || x.getOsDescription().name.equals(config.systemConfig.name))
                        && (x.getOsDescription().arch==null || x.getOsDescription().arch.equals(config.systemConfig.arch))
                        && (x.getOsDescription().version == null || x.getOsDescription().version.equals(config.systemConfig.version))
                        && (x.getAction().equals("allow"))
        ).collect(Collectors.toList());
    }

    private ArrayList<String> resolveLibrary(IVersionLibrary library) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        if(library.getRules() != null)
        {
            List<IVersionRule> librariesList =  Arrays.stream(library.getRules()).filter(
                    x->     x.getOsDescription() == null ||
                            ((x.getOsDescription().name == null || x.getOsDescription().name.equals(config.systemConfig.name))
                            && (x.getOsDescription().arch==null || x.getOsDescription().arch.equals(config.systemConfig.arch))
                            && (x.getOsDescription().version == null || x.getOsDescription().version.equals(config.systemConfig.version)))
            ).collect(Collectors.toList());

            if(librariesList.size() > 0 && librariesList.stream().noneMatch(x->x.getAction().equals("disallow")))
            {
                list.add(library.getDownloadDescription().getArtifact().getPath());
                if(library.getDownloadDescription().getClassifier(config.systemConfig.name) != null)
                    list.add(library.getDownloadDescription().getClassifier(config.systemConfig.name).getPath());
            }
        }
        else
        {
            list.add(library.getDownloadDescription().getArtifact().getPath());
            if(library.getDownloadDescription().getClassifier(config.systemConfig.name)!=null)
                list.add(library.getDownloadDescription().getClassifier(config.systemConfig.name).getPath());
        }


        return list;
    }

    public String setProperties(String command, Config config, IVersionConfig versionConfig) throws Exception
    {
        ArrayList<String> usedLibraries = new ArrayList<>();
        for (IVersionLibrary library : versionConfig.getLibraries())
            usedLibraries.addAll(resolveLibrary(library));

        String librariesCP = "";
        for(String value : usedLibraries)
            librariesCP+=config.modeConfig.buildLibraryPath(value)+":";

        librariesCP+=config.modeConfig.buildJarPath(versionConfig.getID());
        librariesCP+=" "+versionConfig.getMainClass();

        command = command.replace("${natives_directory}", config.modeConfig.buildNativesPath(versionConfig.getID()))
                .replace("${launcher_name}", config.launcherName)
                .replace("${launcher_version}", config.launcherVersion)
                .replace("${classpath}", librariesCP)
                .replace("${auth_player_name}", config.username)
                .replace("${version_name}", versionConfig.getID())
                .replace("${game_directory}", config.root)
                .replace("${assets_root}", config.modeConfig.buildAssetsDir())
                .replace("${version_type}", versionConfig.getType().name())
                .replace("${assets_index_name}", versionConfig.getProperty("assets"))
                .replace("--userType ${user_type}","")
                .replace("${auth_access_token}", "{token}")
                .replace("${auth_uuid}", "{uuid}");

        return command;
    }

}
