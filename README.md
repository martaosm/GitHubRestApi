# GitHubRestApi
Application retrieves all repositories of a given user. Repositories returned are checked if they are not forks. If repository is a fork, it is skipped over and not returned in a response.
</br>
Call to GitHub Api -> Entities returned as an array of Object class objects -> Writing objects to json strings -> Converting strings into JSONObjects -> Accessing required attributes and writing them into response objects -> Returning response
![Zrzut ekranu 2024-02-21 164251](https://github.com/martaosm/GitHubRestApi/assets/56367868/0037b112-fd9b-4236-a7fe-c3b249769f4a)
</br>
Structure of reponse body:</br>
```json
{
    "repositoryName": "name",
    "ownerLogin": "login",
    "branches": [
        {
            "branchName": "main",
            "lastCommitSha": "lastCommitSha"
        }
    ]
}
```

In case of an error, when the user is not found, custom error message is returned, as per instructions.
![Zrzut ekranu 2024-02-21 164202](https://github.com/martaosm/GitHubRestApi/assets/56367868/0b9f7f24-5adb-4315-ad93-eb67e76764a2)

All requests are made with accept header:
![Zrzut ekranu 2024-02-21 164309](https://github.com/martaosm/GitHubRestApi/assets/56367868/f1766290-5013-4c70-9b8b-caa3f0d391bc)

In case of this error, there are few lines to be uncommented and another commented (more instructions in code): 
<ul>
    <li>in GitHubService.java</li>
    <li>whole of a GitHubConfig.java</li>
</ul>

Also there is a need to add generated personal access token. More how to do it under the screenshot. </br>
![Zrzut ekranu 2024-02-21 174455](https://github.com/martaosm/GitHubRestApi/assets/56367868/ace78cd3-7790-4996-8d64-eae84c850bc0)
</br>
Not authenticated users have rate limit set and after reaching it api will return 403 code. After 60 min wait, access is returned, but after few shoots rate limit is reached again. To avoid that user can generate personal access token on GitHub account and include it in configuration of their app. To generate access token:</br> Settings -> Developer Settings -> Personal Access Token</br> There, user can generate it. Generated token can be pasted directly into application.properties file, but it is not advised. Safer option is to create environmental variable and access it from the code.
