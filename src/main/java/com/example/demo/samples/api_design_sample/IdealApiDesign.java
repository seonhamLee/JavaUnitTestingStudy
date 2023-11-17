package com.example.demo.samples.api_design_sample;

public class IdealApiDesign {

	static class UserController { // 클라이언트 코드

		// '사용자(User) 이름 변경' 이라는 목표를 지닌다.
		public void renameUser(int userId, String newName) {
			final User user = getUserFromDatabase(userId);
			user.setName(newName); // User 클래스의 메서드를 한번만 호출한다.
			saveUserToDatabase(user);
		}

		private void saveUserToDatabase(User user) {
		}

		private User getUserFromDatabase(int userId) {
			return new User();
		}
	}

	static class User {

		private String name; // 클라이언트 코드의 목표에 맞물려 있는 속성. getter setter로 노출한다.

		public String getName() {
			return name;
		}

		public void setName(String name) { // 식별 가능한 동작.

			this.name = normalizeName(name);
		}

		private String normalizeName(String name) { // 클라이언트 코드의 목표와 관련 없는 메서드.
			final String result = name.strip();

			if (result.length() > 50) {
				return result.substring(0, 50);
			}
			return result;
		}
	}
}
