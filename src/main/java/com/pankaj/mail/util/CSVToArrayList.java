package com.pankaj.mail.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVToArrayList {
	public static void main(String... args) {
		List<String> emails = readEmailsFromCSV("/home/pkp/Desktop/email.csv");
		// let's print all the person read from CSV file
		for (String e : emails) {
			System.out.println(e);
		}
	}

	public static List<String> readEmailsFromCSV(String fileName) {
		List<String> emails = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			while (line != null) {
				emails = Arrays.asList(line.split(","));
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return emails;
	}

}
