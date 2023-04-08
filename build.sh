#!/usr/bin/env bash

script_dir=$(dirname "$0")
script_file_name=$(basename "$0")

# the absolute dir of project
base_dir=$(
  cd $script_dir
  cd ../..
  pwd
)

function init_java_env() {
  # Select java home version > 13
  JAVA_HOME=$(/usr/libexec/java_home -v 13+)
  echo 'JAVA_HOME: '$JAVA_HOME
  export PATH=$JAVA_HOME/bin:$PATH
  #java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F'.' '{print $1$2}'
}

help_action=(
  'help'
  'h'
  'help: show all actions'
  'welcome'
)

build_action=(
  'build'
  'b'
  'build manager local debugging environment'
  'build'
)

run_action=(
  'run'
  'r'
  'build manager local debugging environment'
  'run'
)

actions=(
  help_action
  build_action
  run_action
)

function welcome() {
  local_date_time=$(date +"%Y-%m-%d %H:%M:%S")
  echo '####################################################################################'
  echo '#                 Welcome to use Flake-todo dev toolkit !                         #'
  echo '#                                        @'$local_date_time'                      #'
  echo '####################################################################################'
  echo ''

  # shellcheck disable=SC2068
  for action in ${actions[@]}; do
    # shellcheck disable=SC1087
    TMP=$action[@]
    TempB=("${!TMP}")
    name=${TempB[0]}
    simple_name=${TempB[1]}
    desc=${TempB[2]}

    echo $script_dir'/'$script_file_name' '$name' | '$simple_name
    echo '      :'$desc
  done
  echo ''
}

function build() {
  echo '# start build...'

  init_java_env
  mvn clean package

  echo 'build finished .'
}

function run() {
  echo '# start run...'

  init_java_env
  mvn clean javafx:run -pl flake-client
}

function main() {
  if [[ "$OSTYPE" != "darwin"* ]] && [[ "$OSTYPE" != "linux-gnu"* ]]; then
    echo "This script only supports macOS or Linux, current OS is $OSTYPE"
    exit 1
  fi

  action=$1

  if [ ! -n "$action" ]; then
    welcome
  fi

  # shellcheck disable=SC2068
  for one_action in ${actions[@]}; do
    # shellcheck disable=SC1087
    TMP=$one_action[@]
    TempB=("${!TMP}")
    name=${TempB[0]}
    simple_name=${TempB[1]}
    function_name=${TempB[3]}
    desc=${TempB[4]}

    if [[ X"$name" == X"$action" ]] || [[ X"$simple_name" == X"$action" ]]; then
      echo 'Execute action: '"$function_name"
      $function_name
    fi
  done

  echo 'Have a nice day, bye!'
}

main $1
