FROM ubuntu:latest
LABEL authors="keyinstudent"

ENTRYPOINT ["top", "-b"]