package com.example.demo.services.manage_audit.v2.file_system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileSystem implements IFileSystem {

	// 파일 시스템 작업을 캡슐화했다.

	@Override
	public List<String> getFiles(String directoryName) {

		final File dir = new File(directoryName);

		return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
				.map(File::getPath)
				.collect(Collectors.toList());
	}

	@Override
	public void writeAllText(String filePath, String content) throws IOException {

		final File newFile = new File(filePath);

		if (newFile.createNewFile()) {
			try (final FileWriter fileWriter = new FileWriter(newFile)) {
				fileWriter.write(content);
			}
		}
		else {
			throw new IllegalStateException("\"audit_1.txt\" named file already exists.");
		}
	}

	@Override
	public List<String> readAllLines(String filePath) throws IOException {

		return Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
	}
}
