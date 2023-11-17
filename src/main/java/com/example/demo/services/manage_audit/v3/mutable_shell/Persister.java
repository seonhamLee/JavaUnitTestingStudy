package com.example.demo.services.manage_audit.v3.mutable_shell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.demo.services.manage_audit.v3.functional_core.input.FileContent;
import com.example.demo.services.manage_audit.v3.functional_core.output.FileUpdate;

// Example 6.13
public class Persister {

	// 함수형 코어에 전해질 입력 데이터를 수집한다.
	public List<FileContent> readDirectory(String directoryName) throws IOException {

		final File dir = new File(directoryName);
		final List<String> filePaths = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
				.map(File::getPath)
				.collect(Collectors.toList());
		List<FileContent> fileContents = new ArrayList<>();
		for (String filePath : filePaths) {
			FileContent fileContent = new FileContent(
					filePath,
					Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8)
			);
			fileContents.add(fileContent);
		}
		return fileContents;
	}

	// 함수형 코어로부터 받은 명령으로 사이드 이펙트를 수행한다.
	public void applyUpdate(String directoryName, FileUpdate update) throws IOException {
		final File newFile = new File(directoryName + "/" + update.fileName);
		if (newFile.createNewFile()) {
			try (final FileWriter fileWriter = new FileWriter(newFile)) {
				fileWriter.write(update.newContent);
			}
		}
		else {
			try (final FileWriter fileWriter = new FileWriter(newFile)) {
				fileWriter.write(update.newContent);
			}
		}
	}
}