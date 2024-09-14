#!/bin/bash

IMAGE_NAME="todo-autotests"

run_api_tests() {
  echo "Launch API tests..."

  # Спрашиваем логин и пароль у пользователя
  read -p "Enter rest login: " LOGIN
  read -sp "Enter rest password: " PASSWORD
  echo

  # Собираем Docker-образ
  echo "Building Docker image..."
  docker build -t $IMAGE_NAME -f api.Dockerfile .

  echo "Importing todo-app docker image..."
  docker load -i ./docker/todo-app.tar

  # Запуск контейнера с API тестами, передаем логин и пароль
  echo "Launch container with API tests..."
  #  docker run --rm -it \
  docker run -it \
    -e LOGIN="$LOGIN" \
    -e PASSWORD="$PASSWORD" \
    -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -p 5555:5555 \
    $IMAGE_NAME
}

run_perf_report() {
  echo "Launch Perf tests report"

  printf "✅ Perf report will be available at: http://127.0.0.1:5556/performance-tests/results/index.html \n\n"

  python3 -m http.server 5556
}

main_menu() {
  # Главное меню выбора
  echo "Select what to launch:"
  echo "1. API tests"
  echo "2. Performance Report"
  read -p "Enter choice number: " CHOICE

  case $CHOICE in
  1)
    run_api_tests
    ;;
  2)
    run_perf_report
    ;;
  *)
    echo "Wrong choice. Please, select 1 or 2"
    ;;
  esac
}

main_menu
