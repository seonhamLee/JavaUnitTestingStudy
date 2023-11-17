package com.example.demo.services.manage_audit.v2.file_system;

import java.io.IOException;
import java.util.List;

public interface IFileSystem {

	List<String> getFiles(String directoryName);

	void writeAllText(String filePath, String content) throws IOException;

	List<String> readAllLines(String filePath) throws IOException;
}