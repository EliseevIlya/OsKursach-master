package com.example.foldermaneger.Methods;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class MethodsAdditionalInformation {
    public  void writeStorageInfoToFile(String fileName) throws IOException {
        // Создание команды для выполнения lsblk

        // Создание процесса для выполнения команды в терминале
        ProcessBuilder processBuilder = new ProcessBuilder( "lsblk");

        // Перенаправление вывода команды в файл
        //processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(fileName)));

        // Запуск процесса и ожидание завершения
        //Process process = null;

        File file = new File(fileName);
//create the file.
        /*if (file.createNewFile()){
            System.out.println("File is created!");
        }
        else{
            System.out.println("File already exists.");
        }*/
        Process command;
        FileWriter writer = new FileWriter (file);
        try {
            command = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String process;
        BufferedReader input=new BufferedReader(new InputStreamReader(command.getInputStream()));
        process=input.readLine();
        while (process!=null){
            writer.write(process);
            writer.write("\n");

            process=input.readLine();
        }
        input.close();
        //write content
        writer.close();

        // Проверка кода завершения процесса
        /*try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Информация о устройстве ПЗУ успешно записана в файл " + fileName);
            } else {
                System.out.println("Возникла ошибка при выполнении команды");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public  void writeVirtualMemoryInfoToFile(String fileName)  {
        // Создание команды для выполнения free и перенаправление вывода в файл
        String command = "free";

        // Создание процесса для выполнения команды в терминале
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(fileName)));

        // Запуск процесса и ожидание завершения
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Проверка кода завершения процесса
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Информация о проценте использования виртуальной памяти успешно записана в файл " + fileName);
            } else {
                System.out.println("Возникла ошибка при выполнении команды");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  int getCurrentPID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeMXBean.getName();
        int indexOfSeparator = jvmName.indexOf('@');

        if (indexOfSeparator > 0) {
            try {
                return Integer.parseInt(jvmName.substring(0, indexOfSeparator));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }
    public  String  getModuleCount(int pid) {
        try {
            Process process = new ProcessBuilder("sh", "-c", "lsmod | awk -v pid=" + pid + " '$NF ~ \"^\"pid\" \" {count++} END {print count}'")
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "0";
    }

}
