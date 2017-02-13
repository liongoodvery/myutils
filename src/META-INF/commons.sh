#!/bin/bash

function fn(){
    find -name "*$1*"
}

function get_date(){
    date +%F-%H-%M.%N
}

function get_log(){
    echo ~/backups/log/$1-`get_date`.log
}

