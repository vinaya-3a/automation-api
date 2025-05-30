package com.example;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC002 {
	public Map<String, Object> triggerTest() {
		Map<String, Object> response = new HashMap<>();
		List<JSONObject> steps = new ArrayList<>();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*"); // prevent "only local connections are allowed"
		options.addArguments("--headless=new"); // optional if no UI is needed

		WebDriver driver = new ChromeDriver(options);

		try {
			long startTime = System.currentTimeMillis();
			// Step 1: Navigate to login page
			JSONObject step1 = new JSONObject();
			step1.put("step", "Navigate to Login Page");

			try {
				driver.get("https://www.saucedemo.com/"); // Replace with your dummy app login URL
				step1.put("status", "PASS - Login page loaded");
			} catch (Exception e) {
				step1.put("status", "failed");
				step1.put("error", e.getMessage());
			}
			steps.add(step1);

			// Step 2: Enter username
			JSONObject step2 = new JSONObject();
			step2.put("step", "Enter Username");
			try {
				WebElement username = driver.findElement(By.id("user-name"));
				username.sendKeys("standard_user");
				step2.put("status", "PASS - Entered username");
			} catch (Exception e) {
				step2.put("status", "failed");
				step2.put("error", e.getMessage());
			}
			steps.add(step2);

			// Step 3: Enter password
			JSONObject step3 = new JSONObject();
			step3.put("step", "Enter Password");
			try {
				WebElement password = driver.findElement(By.id("password"));
				password.sendKeys("secret_sauce");
				step3.put("status", "PASS - Entered password");
			} catch (Exception e) {
				step3.put("status", "failed");
				step3.put("error", e.getMessage());
			}
			steps.add(step3);

			// Step 4: Click login button
			JSONObject step4 = new JSONObject();
			step4.put("step", "Click Login Button");
			try {
				WebElement login = driver.findElement(By.id("login-button"));
				login.click();
				step4.put("status", "Pass - Logged in successfully");
			} catch (Exception e) {
				step4.put("status", "failed");
				step4.put("error", e.getMessage());
			}
			steps.add(step4);

			Thread.sleep(3000); // Wait for page load (replace with WebDriverWait in real code)

			// Step 5: Verify login result
			JSONObject step5 = new JSONObject();
			step5.put("step", "Verify Login Success");
			try {
				boolean isSuccess = driver.getCurrentUrl().contains("inventory");
				step5.put("status", isSuccess ? "passed" : "failed");
			} catch (Exception e) {
				step5.put("status", "FAIL - Did not reach dashboard");
				step5.put("error", e.getMessage());
			}
			steps.add(step5);

			driver.quit();

			long endTime = System.currentTimeMillis();
			double duration = (endTime - startTime) / 1000.0;

			// Final report
			JSONObject report = new JSONObject();
			report.put("timestamp", LocalDateTime.now().toString());
			report.put("testName", "Login Test");
			report.put("duration", duration);
			report.put("steps", steps);

			Files.createDirectories(Paths.get("reports"));
			Files.write(Paths.get("reports/report.json"), report.toString(4).getBytes(StandardCharsets.UTF_8));

			response.put("status", "success");
			response.put("result", report.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "failed");
			response.put("error", e.getMessage());
		}
		return response;
	}
}
