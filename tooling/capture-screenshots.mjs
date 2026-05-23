import { chromium } from "playwright";
import path from "node:path";
import { pathToFileURL } from "node:url";

const rootDir = "C:/Users/Administrator/Desktop/AI面试";
const docsDir = path.join(rootDir, "docs");

const browser = await chromium.launch({ headless: true });
const page = await browser.newPage({ viewport: { width: 1440, height: 1080 } });

await page.goto("http://localhost:8080/swagger-ui.html", { waitUntil: "networkidle" });
await page.screenshot({ path: path.join(docsDir, "api-test-screenshot-swagger.png"), fullPage: true });

await page.goto(pathToFileURL(path.join(docsDir, "summary-response.html")).href, { waitUntil: "load" });
await page.screenshot({ path: path.join(docsDir, "api-test-screenshot-summary.png"), fullPage: true });

await page.goto(pathToFileURL(path.join(docsDir, "qa-response.html")).href, { waitUntil: "load" });
await page.screenshot({ path: path.join(docsDir, "api-test-screenshot-qa.png"), fullPage: true });

const extraPages = [
  ["mvnw-test-log.html", "run-test-screenshot-mvnw-test.png"],
  ["docker-run-log.html", "run-test-screenshot-docker.png"]
];

for (const [htmlName, imageName] of extraPages) {
  await page.goto(pathToFileURL(path.join(docsDir, htmlName)).href, { waitUntil: "load" });
  await page.screenshot({ path: path.join(docsDir, imageName), fullPage: true });
}

await browser.close();
