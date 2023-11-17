package com.example.demo.services.manage_audit.v3.functional_core.output;

// Example 6.12
public class FileUpdate {

	public final String fileName;

	public final String newContent;

	public FileUpdate(String fileName, String newContent) {
		this.fileName = fileName;
		this.newContent = newContent;
	}
}