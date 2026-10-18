# 🫀 mobile-cardio

[![CI — Build & Check](https://github.com/invercity/mobile-cardio/actions/workflows/ci.yml/badge.svg)](https://github.com/invercity/mobile-cardio/actions/workflows/ci.yml)
[![Latest Tag](https://img.shields.io/github/v/tag/invercity/mobile-cardio?include_prereleases&sort=semver&label=latest%20tag)](https://github.com/invercity/mobile-cardio/tags)
[![Android](https://img.shields.io/badge/Android-5.0%20--%2015%20(API%2021--35)-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

Сучасний Android-додаток для перегляду та аналізу кардіограм у цифровому медичному стандарті **SCP-ECG (EN 1064 / ANSI/AAMI EC71 / DICOM)**.

---

## ✨ Основні можливості

* **Підтримка стандарту SCP-ECG:** Вбудований парсер секцій 0–6, Huffman-декодування, відновлення відведень та частотних характеристик.
* **Апаратно-прискорений рендеринг ЕКГ:** Малювання відведень та міліметрівки (1 мм / 5 мм) на нативному `android.graphics.Canvas`.
* **Масштабування та навігація:** Жести pinch-to-zoom, плавний скролінг, налаштування швидкості розгортки (12.5, 25, 50, 100 мм/с) та вольтажу (2.5, 5, 10, 20 мм/мВ).
* **Інструмент вимірювань (Лінійка):** Інтерактивне вимірювання інтервалів та амплітуд у міліметрах безпосередньо на графіку.
* **Сучасний доступ до файлів:** Підтримка системного **Storage Access Framework (SAF)** — відкриття `.scp` файлів з внутрішньої пам'яті, Google Drive, SD-карти, месенджерів або завантажень без застарілих дозволів на диск.
* **Завантаження через QR-код:** Швидке відкриття ЕКГ за посиланням через зчитування QR-коду.
* **Дані пацієнта та приладу:** Перегляд паспортних даних, тиску, анамнезу та характеристик кардіографа через адаптивний інтерфейс з Material табами.

---

## 🛠 Технічний стек

* **Платформа:** Android SDK (Min SDK: `21` / Android 5.0 Lollipop — Target SDK: `34` / `35` Android 14/15)
* **Система збірки:** Gradle 8.7, Android Gradle Plugin 8.4+
* **Мова:** Java 17
* **UI та архітектура:** AndroidX (`AppCompat`, `Fragment`, `Preference`), Google Material Components (`TabLayout`)
* **CI/CD:** GitHub Actions (автоматична компіляція debug-білдів на кожен push/PR та релізні збірки з підписаним `.apk` при публікації релізу)

---

## 🚀 Збірка проекту

### Вимоги:
* **JDK 17**
* **Android SDK** (з платформою `android-34` та `build-tools;34.0.0`)

### Команди:

1. **Клонування репозиторію:**
   ```bash
   git clone git@github.com:invercity/mobile-cardio.git
   cd mobile-cardio
   ```

2. **Збірка налагоджувального APK (Debug):**
   ```bash
   ./gradlew assembleDebug
   ```
   Згенерований файл буде доступний за шляхом:  
   `app/build/outputs/apk/debug/app-debug.apk`

3. **Збірка релізного APK (Release):**
   ```bash
   ./gradlew assembleRelease
   ```
   Згенерований файл буде доступний за шляхом:  
   `app/build/outputs/apk/release/app-release.apk`

---

## 📦 Релізи та встановлення

Кожен push у гілку `master` або `main` автоматично:
1. Запускає повну перевірку та збірку релізного APK.
2. Автоматично визначає версію згідно з [SemVer](https://semver.org) на базі повідомлень комітів (`feat`, `fix`, `BREAKING CHANGE`).
3. Публікує **GitHub Release** та прикріплює готовий файл `mobile-cardio-vX.Y.Z.apk`, доступний для встановлення на смартфони та планшети.

---

## 📄 Ліцензія

Проект поширюється на умовах відкритої ліцензії [MIT](LICENSE).
