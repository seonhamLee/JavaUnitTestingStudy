package com.example.demo.chapter6;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.services.manage_audit.v2.AuditManager_V2;
import com.example.demo.services.manage_audit.v2.file_system.IFileSystem;
import com.example.demo.services.manage_audit.v3.functional_core.AuditManager_V3;
import com.example.demo.services.manage_audit.v3.functional_core.input.FileContent;
import com.example.demo.services.manage_audit.v3.functional_core.output.FileUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class AuditManagerTest {

	// AuditManager_V2
	@Test
	void A_new_file_is_created_when_the_current_file_overflows_v2() throws Exception {

		// Given
		final String directoryName = "audits";
		final IFileSystem fileSystemMock = mock(IFileSystem.class);
		given(fileSystemMock.getFiles(directoryName)).willReturn(
				List.of("audits/audit_1.txt", "audits/audit_2.txt")
		);
		given(fileSystemMock.readAllLines("audits/audit_2.txt")).willReturn(
				List.of("Peter; 2019-04-06T16:30:00", "Jane; 2019-04-06T16:40:00", "Jack; 2019-04-06T17:00:00")
		);
		final AuditManager_V2 sut = new AuditManager_V2(3, directoryName, fileSystemMock);

		// When
		sut.addRecord("Alice", LocalDateTime.parse("2019-04-06T18:00:00"));

		// Then
		then(fileSystemMock).should().writeAllText("audits/audit_3.txt", "Alice;2019-04-06T18:00:00");

		// 파일 시스템은 클라이언트가 식별할 수 있는 sut의 사이드 이펙트이므로, 목으로 검증해도 된다.
		// V1을 테스트할 경우와 비교해, 빠른 피드백이 개선되었다.
	}

	// AuditManager_V3
	@Test
	void A_new_file_is_created_when_the_current_file_overflows_v3() throws Exception {

		// Given
		final AuditManager_V3 sut = new AuditManager_V3(3);

		final List<FileContent> files = List.of(
				FileContent.builder()
						.fileName("audit_1.txt")
						.lines(new ArrayList<>())
						.build(),
				FileContent.builder()
						.fileName("audit_2.txt")
						.lines(List.of(
								"Peter; 2019-04-06T16:30:00",
								"Jane; 2019-04-06T16:40:00",
								"Jack; 2019-04-06T17:00:00"
						))
						.build()
		);

		// When
		FileUpdate update = sut.addRecord(files,
				"Alice",
				LocalDateTime.parse("2019-04-06T18:00:00"));

		// Then
		Assertions.assertEquals("audit_3.txt", update.fileName);
		Assertions.assertEquals("Alice;2019-04-06T18:00:00", update.newContent);

		// 목을 사용한 V2 테스트에 비해 유지보수성이 개선되었다.
	}
}
