package com.example.demo.services.manage_audit.v3.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.services.manage_audit.v3.functional_core.AuditManager_V3;
import com.example.demo.services.manage_audit.v3.functional_core.input.FileContent;
import com.example.demo.services.manage_audit.v3.functional_core.output.FileUpdate;
import com.example.demo.services.manage_audit.v3.mutable_shell.Persister;
import lombok.RequiredArgsConstructor;

// Example 6.14
@RequiredArgsConstructor
public class ApplicationService {

	// 함수형 코어와 가변 셸을 이어붙여, 하나의 동작 흐름을 만들어낸다.

	private final String directoryName;

	private final AuditManager_V3 auditManager;

	private final Persister persister;

	public void addRecord(String visitorName, LocalDateTime timeOfVisit) throws IOException {

		// 가변셸에서 함수형 코어에 전해줄 입력 데이터를 수집한다.
		final List<FileContent> files = persister.readDirectory(directoryName);

		// 함수형 코어에 수집한 입력 데이터를 입력하고, 명령 데이터를 출력받는다.
		final FileUpdate update = auditManager.addRecord(files, visitorName, timeOfVisit);

		// 출력받은 명령 데이터를 가변셸에 전해서 사이드 이펙트를 일으킨다.
		persister.applyUpdate(directoryName, update);
	}
}