VERSION=`git rev-parse HEAD`
BUILD=`date +%FT%T%z`
LDFLAGS=-ldflags "-X main.Version=${VERSION} -X main.Build=${BUILD}"
APPLICATION=sancom
PROJECT=requestflows


.PHONY: help
help: ## - Show help message
	@printf "\033[32m\xE2\x9c\x93 usage: make [target]\n\n\033[0m"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: build
build:	## - Building docker image
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Building docker image\n\033[0m"
	@docker build  --build-arg SECRET='$(SECRET)' -f Dockerfile -t gcr.io/$(PROJECT)/$(APPLICATION):$(VERSION) .

.PHONY: build-no-cache
build-no-cache:	## - Building docker image with no cache
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Building docker image with no cache\n\033[0m"
	@docker build --build-arg SECRET='$(SECRET)' --no-cache -f Dockerfile -t gcr.io/$(PROJECT)/$(APPLICATION):$(VERSION) .

.PHONY: ls
ls: ## - Listing images of the application with versions
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Listing images of the application with versions !\n\033[0m"
	@docker image ls gcr.io/$(PROJECT)/$(APPLICATION)

.PHONY: run
run:	## - Run the latest built docker image
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Run the latest built docker image\n\033[0m"
	@docker run --name $(APPLICATION) -d -p 8080:8080 gcr.io/$(PROJECT)/$(APPLICATION):$(VERSION)

.PHONY: stop
stop: ## - stoping the running container for application
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Stoping the running container for application\n\033[0m"
	@docker stop $(APPLICATION) && docker rm $(APPLICATION)

.PHONY: app-compile
app-compile: ## - Compiling the code to single executable file
	@printf "\033[32m\xE2\x9c\x93 $(VERSION) | Compiling the code to single executable file\n\033[0m"
	@mvn clean package spring-boot:repackage