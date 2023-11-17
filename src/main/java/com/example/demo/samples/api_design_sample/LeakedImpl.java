package com.example.demo.samples.api_design_sample;

public class LeakedImpl {

	static class UserController { // 클라이언트 코드

		// '사용자(User) 이름 변경' 이라는 목표를 지닌다.
		public void renameUser(int userId, String newName) {

			final User user = getUserFromDatabase(userId);
			user.name = user.normalizeName(newName); // User 클래스의 메서드를 두번 호출한다.
			saveUserToDatabase(user);
		}

		private void saveUserToDatabase(User user) {
		}

		private User getUserFromDatabase(int userId) {
			return new User(); // 더미를 반환하는 페이크
		}
	}

	static class User {

		public String name; // 클라이언트 코드의 목표에 맞물려 있는 속성. 공개해도 되지만, getter setter로 노출해야 한다.

		public String normalizeName(String name) { // 클라이언트 코드의 목표와 관련 없는 메서드. 비공개로 전환해야 한다.
			final String result = name.strip();

			if (result.length() > 50) {
				return result.substring(0, 50);
			}
			return result;
		}
	}
}
