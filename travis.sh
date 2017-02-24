if [[ ${TRAVIS_BRANCH} == 'master' ]]
then
  echo "Building Maven Site and deploying to GitHub pages"
  mvn site:site
  # mvn site used to do this, but now API rate limiting makes it a non starter
  cd target/site
  git config --global user.email "Travis CI"
  git config --global user.name "tung-jin-chew-hp@users.noreply.github.com"
  echo "Creating repo"
  git init
  echo "Adding remote"
  git remote add origin "git@github.com:${TRAVIS_REPO_SLUG}"
  echo "Adding all the files"
  git add .
  echo "Committing"
  git commit -m "Update GitHub Pages"
  echo "Pushing"
  ls -ltr ../../
  mkdir .ssh
  echo "Extracting Keys"
  cp ../../java-powerpoint-report-deploy-key.gpg .ssh/java-powerpoint-report-deploy-key.gpg
  echo ${GPG_KEY} > tmp.txt && gpg --batch --passphrase-fd 3 3<tmp.txt .ssh/java-powerpoint-report-deploy-key.gpg
  chmod go-rw -R .ssh
  echo 'ssh -i '${PWD}'/.ssh/java-powerpoint-report-deploy-key "$@"' > git-ssh-wrapper
  chmod +x git-ssh-wrapper
  GIT_SSH="${PWD}/git-ssh-wrapper" git push --force origin master:gh-pages
fi