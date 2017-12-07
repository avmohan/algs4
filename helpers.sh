# Some helpers to be sourced into the terminal

# Recursively clean up all *.class files
clean_java_class_files() {
    find . -type f -name '*.class' -delete
}


# Remove all package declarations for submission
remove_package_declarations() {
    find . -type f -name '*.java' -exec sed -i 's/^package.*;//g' '{}' \;
}
