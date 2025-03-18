# report1-42

## 0 Team member

孙璟琦 12311018

庞瑞哲 12311006 (group leader)

唐显智 12312910

张意恒 12311013

## 1 Preliminary Requirement Analysis

### 1.1 Functional requirements:

1. **Act as a Useful IDE**

   Our Intelligent Course-Aware IDE is designed with education and simplicity in mind, allowing students and educators to start coding instantly. When you're writing code, our IDE provides a **straightforward and clean interface**, free from distractions. Its intuitive design ensures that you can focus on learning and teaching, no matter where you are. Experience the perfect blend of functionality and ease-of-use with our IDE, making classroom coding more efficient and enjoyable. 

2. **AI assistant**

   The Intelligent Course-Aware IDE is integrated with advanced AI tools, such as **OpenAI**, **DeepSeek**, or custom APIs, to provide **real-time, context-aware assistance** for both coding and course-related queries. This AI-powered assistant acts as a 24/7 learning companion, helping students navigate complex concepts, debug code, and deepen their understanding of course materials. 

3. **Course Resource Management.**

   The Course Resource Management feature is designed to provide students with a **secure, intuitive, and well-organized** way to manage all their course materials. Whether it’s lecture slides, assignments, reading materials, or personal notes, this feature ensures that everything is easily accessible, logically structured, and protected against unauthorized access.

4. **Course Tools**

   The Course Tools feature is designed to enhance the learning experience by integrating essential academic tools directly into the IDE. Includes **small IDE**,a powerful **File Reader** and an intuitive **Note Taker**, designed to streamline your study process and keep all your learning materials organized in one place. Whether you’re reviewing lecture slides, annotating PDFs, or jotting down key concepts, these tools ensure that you can focus on learning without the hassle of switching between multiple apps.

5. **Account System & Friend Chatting**

   The Account System & Friend Chatting feature is designed to create a **personalized and collaborative** learning environment. By integrating a robust account system with seamless communication tools, this feature ensures that students can manage their profiles, connect with peers, and collaborate effectively—all within the IDE

   

   

### 1.2 Non-functional requirements:

1. **Performance:**

   - *Response Time:*
     The IDE should load course materials, execute code snippets, and provide AI-generated responses within **2 seconds** for 95% of requests under normal load conditions.

   - *Concurrency:*
     The system should support **up to 1,000 concurrent users** without degradation in performance.

2. **Security:**

   - *Encryption:*
     Manually encrypting data using **SHA256**

   - *Authentication:*
     The system must support **OAuth 2.0** for secure login.

3. **Usability:**

   - The interface is logical, users with vscode, idea and other experience can get started directly. And reduce the learning cost of new users

4. **Maintainability:**

   - *Code Quality:*
     The codebase should adhere to **Certain code principal**
   - *Modularity:*
     The system should be designed with modular architecture to allow easy updates, bug fixes, and feature additions.
   - *Documentation:*
     Comprehensive documentation, including API references, user guides, and developer manuals, must be provided and updated regularly.

5. **Cost Efficiency:**

   - The system should be designed to keep operational costs low, ensuring it remains **affordable for schools and students.**

   - Reduce the development cost and **make the development simple and efficient**



### 1.3 Data requirements:

- *User account data information:* 

  authorized by the user, get specific information from github and other platforms

- *Class files, course information files:* 

  uploaded and maintained by the course leader



### 1.4 Technical requirements:

#### 1.4.1 Operating Environment

- **Operating Environment:** mac
- **IDE Platforms**: IDEA, neovim , screenbuilder.

#### 1.4.2 Tech Stack

- **Frontend**:
  - javafx for interactive UI components.
  - HTML/CSS for layout and styling.
- **Backend**:
  - springboot for server-side logic.
  - PostgreSQL for storing user data, course materials, and progress tracking.
  - HTTP protocol for real-time communication (collaborative coding, chat).
- **Project Management**:
  - Maven for manages dependencies, compiles code, runs tests, and packages projects
  - git for version control and collaboration on coding
  - github for code reviews,Progress presentation, project release and issue tracking.

















