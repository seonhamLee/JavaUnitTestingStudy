package com.example.demo.services.manage_audit.v2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import com.example.demo.services.manage_audit.v2.file_system.IFileSystem;
import lombok.AllArgsConstructor;

// Example 6.9, 6.10
// Mock 활용
@AllArgsConstructor
public class AuditManager_V2 {

	// IFileSystem이라는 새로운 사용자 정의 인터페이스를 활용했다.
	// 이제 sut가 AuditManager일 경우, IFileSystem을 mocking할 수 있다.

	private final int maxEntriesPerFile;

	private final String directoryName;

	private final IFileSystem fileSystem;

	public void addRecord(String visitorName, LocalDateTime timeOfVisit) throws IOException {

		// 작업 디렉토리에서 전체 파일 목록을 검색한다.
		final List<String> filePaths = fileSystem.getFiles(directoryName);

		// 인덱스별로 정렬한다.
		final List<Entry<Integer, String>> sorted = sortByIndex(filePaths);

		// 파일 생성을 위한 단일 레코드를 생성한다.
		final String newRecord = String.format("%s;%s",
				visitorName,
				timeOfVisit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
		);

		// 감사 파일이 없으면 단일 레코드로 첫번째 파일을 생성한다.
		if (sorted.isEmpty()) {
			final String newFilePath = directoryName + "/audit_1.txt";
			fileSystem.writeAllText(newFilePath, newRecord);
			return;
		}

		// 감사 파일이 있으면 있는 파일 중 가장 최신 것을 가져온다.
		final Entry<Integer, String> latestFile = sorted.get(sorted.size() - 1);
		final int currentFileIndex = latestFile.getKey();
		final String currentFilePath = latestFile.getValue();

		// 가져온 최신 파일의 항목 수가 한계에 도달했는지를 확인한다.
		final List<String> lines = fileSystem.readAllLines(currentFilePath);

		// 한계에 도달하지 않았다면 새 레코드를 추가한다.
		if (lines.size() < maxEntriesPerFile) {
			lines.add(newRecord);
			fileSystem.writeAllText(currentFilePath, String.join("\r\n", lines));
		}

		// 한계에 도달했다면 새 파일을 생성한다.
		else {
			final int newIndex = currentFileIndex + 1;
			final String newName = String.format("audit_%d.txt", newIndex);
			fileSystem.writeAllText(directoryName + "/" + newName, newRecord);
		}
	}

	private List<Entry<Integer, String>> sortByIndex(List<String> files) {
		final Map<Integer, String> map = new HashMap<>();
		IntStream.range(0, files.size())
				.forEach(i -> map.put(getIndex(files.get(i)), files.get(i)));

		final List<Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
		entries.sort(Entry.comparingByKey());
		return entries;
	}

	private int getIndex(String filePath) {
		final int underscoreIdx = filePath.lastIndexOf("_");
		final int dotIdx = filePath.lastIndexOf(".");
		return Integer.parseInt(filePath.substring(underscoreIdx + 1, dotIdx));
	}
}