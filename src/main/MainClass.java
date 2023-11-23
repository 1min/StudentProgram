package main;

import java.util.Scanner;

import dao.StudentDao;


public class MainClass {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		// menu
		StudentDao dao = new StudentDao();

		while (true) {
			System.out.println("========= 학생정보 프로그램 ==========");
			System.out.println("1.학생정보추가");
			System.out.println("2.학생정보삭제");
			System.out.println("3.학생정보검색");
			System.out.println("4.학생정보수정");
			System.out.println("5.학생정보 모두출력");
			System.out.println("6.학생정보저장");
			System.out.println("7.학생정보불러오기");

			System.out.print("메뉴의 번호 >> ");
			int menuNumber = sc.nextInt();

			switch (menuNumber) {
				case 1:
					dao.insert();
					break;
				case 2:
					dao.delete();
					break;
				case 3:
					dao.select();
					break;
				case 4:
					dao.update();
					break;
				case 5:
					dao.allData();
					break;
				case 6:
					dao.save();
					break;
				case 7:
					dao.load();
					break;
				default:
					break;
			}
		}
	}

}
