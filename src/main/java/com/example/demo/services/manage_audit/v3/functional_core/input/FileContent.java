package com.example.demo.services.manage_audit.v3.functional_core.input;

import java.util.List;

import lombok.Builder;

// Example 6.12
@Builder
public record FileContent(String fileName, List<String> lines) {

}