package com.example.demo.services.manage_audit.v3.functional_core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import com.example.demo.services.manage_audit.v3.functional_core.input.FileContent;
import com.example.demo.services.manage_audit.v3.functional_core.output.FileUpdate;
import lombok.AllArgsConstructor;

// Example 6.12

@AllArgsConstructor
public class AuditManager_V3 {

	// 함수형 아키텍처로 전환하였다. 해당 클래스는 함수형 코어에 해당된다.

	private final int maxEntriesPerFile;

	public FileUpdate addRecord(
			List<FileContent> files, // 이제 작업 디렉토리 대신 FileContent 배열을 받는다.
			String visitorName,
			LocalDateTime timeOfVisit
	) throws IOException {

		// 입력받은 FileContent를 인덱스별로 정렬한다.
		final List<Entry<Integer, FileContent>> sorted = sortByIndex(files);

		// 파일 생성을 위한 단일 레코드를 생성한다.
		final String newRecord = String.format("%s;%s",
				visitorName,
				timeOfVisit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
		);

		// 감사 파일이 없으면 단일 레코드로 첫번째 파일을 생성한다.
		if (sorted.isEmpty()) {
			return new FileUpdate("audit_1.txt", newRecord); // 이제 가변셸에 전해질 사이드 이펙트 '명령'을 반환한다.
		}

		// 감사 파일이 있으면 있는 파일 중 가장 최신 것을 가져온다.
		final Entry<Integer, FileContent> latestFile = sorted.get(sorted.size() - 1);
		final Integer currentFileIndex = latestFile.getKey();
		final FileContent currentFile = latestFile.getValue();

		// 가져온 최신 파일의 항목 수가 한계에 도달했는지를 확인한다.
//		final List<String> lines = Files.readAllLines(Path.of(currentFile.fileName()), StandardCharsets.UTF_8);
		final List<String> lines = currentFile.lines();

		// 한계에 도달하지 않았다면 새 레코드를 추가한다.
		if (lines.size() < maxEntriesPerFile) {
			lines.add(newRecord);
			final String newContent = String.join("\r\n", lines);
			return new FileUpdate(currentFile.fileName(), newContent);
		}

		// 한계에 도달했다면 새 파일을 생성한다.
		else {
			final int newIndex = currentFileIndex + 1;
			final String newName = String.format("audit_%d.txt", newIndex);
			return new FileUpdate(newName, newRecord);
		}
	}

	private List<Entry<Integer, FileContent>> sortByIndex(List<FileContent> files) {
		final Map<Integer, FileContent> map = new HashMap<>();
		IntStream.range(0, files.size())
				.forEach(i -> map.put(getIndex(files.get(i).fileName()), files.get(i)));

		final List<Entry<Integer, FileContent>> entries = new ArrayList<>(map.entrySet());
		entries.sort(Entry.comparingByKey());
		return entries;
	}

	private int getIndex(String filePath) {
		final int underscoreIdx = filePath.lastIndexOf("_");
		final int dotIdx = filePath.lastIndexOf(".");
		return Integer.parseInt(filePath.substring(underscoreIdx + 1, dotIdx));
	}
}