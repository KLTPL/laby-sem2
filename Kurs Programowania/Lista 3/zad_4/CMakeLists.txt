cmake_minimum_required(VERSION 3.10)

project(FigureTest)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include_directories(geometry)

set(SOURCES
    main.cpp
    Figure.cpp
  )

add_executable(FigureTest ${SOURCES})
