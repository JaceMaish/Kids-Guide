# KidsGuide 👦👧

**KidsGuide** is a modern, interactive learning companion designed to help children master core subjects while providing parents with the tools to monitor their educational journey. Built with a focus on engagement and ease of use, it bridges the gap between fun learning and parent-involved education.

---

## 🚀 Features

### 👦 For Children
- **Interactive Lessons:** Engaging content across multiple subjects including Reading, Mathematics, Science, Social Studies, and Creative Arts.
- **Gamified Quizzes:** Reinforce learning through instant-feedback quizzes that challenge and motivate.
- **Progressive Learning:** Structured subject paths that guide children through their educational milestones.

### 👨‍👩‍👧 For Parents
- **Real-time Dashboard:** A high-level overview of the child's performance, including lessons completed and average scores.
- **Detailed Progress Tracking:** Visual progress bars for every subject to identify strengths and areas needing more attention.
- **Comprehensive Reports:** Deep dives into quiz performance and learning history.

---

## 🛠 Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern declarative UI framework.
- **Navigation:** [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) - Seamless transitions between screens.
- **Backend:** 
  - **Firebase Auth:** Secure user authentication for parents and child roles.
  - **Firestore:** Scalable NoSQL database for storing lesson content and student progress.
- **Language:** [Kotlin](https://kotlinlang.org/) - 100% modern, concise code.
- **Theming:** Material 3 Design System for a clean and kid-friendly aesthetic.

---

## 📸 Screenshots

| Role Selection | Child Home | Parent Dashboard |
| :---: | :---: | :---: |
| ![Role Selection](https://via.placeholder.com/200x400?text=Role+Select) | ![Child Home](https://via.placeholder.com/200x400?text=Child+Home) | ![Parent Dashboard](https://via.placeholder.com/200x400?text=Dashboard) |

---

## 🏗 Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/Kids-Guide.git
   ```
2. **Setup Firebase:**
   - Add your `google-services.json` to the `app/` directory.
   - Enable Email/Password authentication in the Firebase Console.
   - Setup Firestore with the required lesson collections.
3. **Build & Run:**
   - Open the project in Android Studio (Ladybug or newer recommended).
   - Sync Gradle and run the `:app` module on an emulator or physical device.

---

## 🛣 Roadmap
- [ ] Push notifications for parent daily summaries.
- [ ] Offline mode support for lessons.
- [ ] Reward system (badges/stars) for quiz completion.
- [ ] Multi-child support for a single parent account.

---

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

---
*Created with ❤️ for the next generation of learners.*
