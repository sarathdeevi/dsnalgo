package com.sdeevi.patterns.commandexecutor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutorGenerator {

    private static String generateCommandEnum(List<String> lines) throws IOException {
        List<String> enumValues = new ArrayList<>();
        for (String line : lines) {
            String command = line.split(",")[0];
            enumValues.add((command.toUpperCase()) + "(\"" + command + "\")");
        }
        String commandEnum = readFile("commandenum.txt");
        return commandEnum.replace("{{enumValues}}", StringUtils.join(enumValues, ",\n\t") + ";");
    }

    private static String generateCommandExecFactory(List<String> lines, String appName) throws IOException {
        List<String> cases = new ArrayList<>();
        for (String line : lines) {
            String command = line.split(",")[0];
            cases.add("case " + (command.toUpperCase()) + ":\n\t\t\t\t"
                    + "executor = new " + StringUtils.capitalize(command.toLowerCase()) + "CommandExecutor(service, commandArgs);\n\t\t\t\tbreak;\n\t\t\t");
        }
        String executorFactory = readFile("commandexecutorfactory.txt");
        return executorFactory.replaceAll("\\{\\{cases}}", StringUtils.join(cases, ""))
                .replaceAll("\\{\\{appName}}", appName);
    }

    private static String generateCommandExecService(String appName) throws IOException {
        String execService = readFile("commandexecservice.txt");
        return execService.replaceAll("\\{\\{appName}}", appName);
    }

    private static String generateCommandImpls(List<String> lines, String appName) throws IOException {
        final String commandExecImpl = readFile("commandexecimpl.txt");
        StringBuffer sb = new StringBuffer();
        for (String line : lines) {
            String[] command = line.split(",");
            sb.append(commandExecImpl.replaceAll("\\{\\{appName}}", appName)
                    .replaceAll("\\{\\{numArgs}}", command[1])
                    .replaceAll("\\{\\{name}}", StringUtils.capitalize(command[0].toLowerCase()))).append("\n\n");
        }
        return sb.toString();
    }

    private static String generateService(List<String> lines, String appName) throws IOException {
        final String service = readFile("service.txt").replaceAll("\\{\\{appName}}", appName);
        List<String> methods = new ArrayList<>();
        for (String line : lines) {
            String[] command = line.split(",");
            methods.add("public String execute" + StringUtils.capitalize(command[0].toLowerCase()) + "(String param) {\n" +
                    "\t\treturn null;\n\t}");
        }
        return service.replaceAll("\\{\\{methods}}", StringUtils.join(methods, "\n\n\t"));
    }

    private static String generateSoln(String appName) throws IOException {
        final String soln = readFile("solution.txt");
        return soln.replaceAll("\\{\\{appName}}", appName);
    }

    private static String readFile(String fileName) throws IOException {
        return StringUtils.join(IOUtils.readLines(CommandExecutorGenerator.class.getResourceAsStream(fileName), Charset.defaultCharset()), "\n");
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("Add,2");
        lines.add("uPdAtE,1");
        lines.add("deLeTe,1");
        String appName = "MyApp";

        File file = new File("src/main/java/com/sdeevi/apps/" + appName.toLowerCase() + "/Solution.java");
        StringBuffer sb = new StringBuffer();
        sb.append("package com.sdeevi.patterns.").append(appName.toLowerCase()).append(";\n\n").append("import java.util.HashMap;\n").append("import java.util.Map;\n").append("import java.util.Scanner;\n\n");
        sb.append(generateCommandEnum(lines)).append("\n\n");
        sb.append(readFile("commandexecutor.txt")).append("\n\n");
        sb.append(generateCommandExecFactory(lines, appName)).append("\n\n");
        sb.append(readFile("exception.txt")).append("\n\n");
        sb.append(generateCommandExecService(appName)).append("\n\n");
        sb.append(generateCommandImpls(lines, appName)).append("\n\n");
        sb.append(generateService(lines, appName)).append("\n\n");
        sb.append(readFile("stringutils.txt")).append("\n\n");
        sb.append(generateSoln(appName)).append("\n");

        FileUtils.write(file, sb.toString(), Charset.defaultCharset());
    }
}
