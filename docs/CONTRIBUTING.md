# Contributing

## How to Contribute

### 1. Fork the Repository

First, you need to fork the repository to create a copy under your own Github account:

* Click the "Fork" button at the top right of the repository page to create your own copy of the repository

### 2. Clone the Repository

Next, clone the forked repository to your local machine:

```bash
git clone https://github.com/your-username/project-name.git
```

### 3. Set Upstream Remote

To keep your fork in sync with the original project, set the original repository as the upstream remote:

```bash
git remote add upstream git@github.com:sustech-cs304/team-project-25spring-42.git
```

Once you have done this, you can keep your repo in sync with the original project, you can keep the changes of original project by:

```bash
git fetch upstream
```

When you are able to develop the new feature, it is strongly recommended to execute the command above first.

### 4. Create a Branch

Before making any changes, create a new branch from develop for your feature or bug fix:

```bash
git checkout --track origin/develop
git checkout -b feature/my-feature
```

### 5. Make Changes and Commit

Make your changes, then add and commit them:

```
git add .
git commit -m "Add new features"
git push origin feature/my-feature
```

### 6. Open a Pull Request

Once you're ready to contribute, open a Pull Request (PR) to merge your changes into the original repository. You can do this by navigating to the GitHub page of the project and clicking on "New Pull Request" from your branch (e.g., feature/my-feature) to develop branch of the original repository.
