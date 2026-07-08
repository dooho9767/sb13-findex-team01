@echo off

git config --local commit.template .gitmessage.txt
git config --local core.hooksPath .githooks

echo Git commit template and hooks have been configured.
echo Commit message example: Add: 지수 정보 생성 API 추가

pause