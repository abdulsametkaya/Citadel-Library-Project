package com.library.citadel_library.exception.message;

public class ErrorMessage {

    public final static String LOAN_TIME_INCORRECT_MESSAGE ="Loan pick up time or drop off time not correct";
    public final static String USER_NOT_FOUND_MESSAGE="User with email %s not found";
    public final static String EMAIL_ALREADY_EXIST_MESSAGE="Email already exist: %s";
    public final static String ROLE_NOT_FOUND_MESSAGE="Role with name %s not found";
    public final static String BOOK_NOT_FOUND_MESSAGE="Book with id %d not found";
    public final static String ACTIVE_BOOK_NOT_FOUND_MESSAGE="Active book with id %d not found";
    public final static String BOOK_NOT_AVAILABLE_TO_REMOVE_MESSAGE="Book with id %d not available to remove";
    public final static String INVALID_BOOK_PARAMETER_MESSAGE="Please write valid parameter";
    public final static String LOAN_NOT_FOUND_MESSAGE="Loan with id %d not found";

    public final static String INVALID_USER_PARAMETER_MESSAGE="Please provide a valid parameter";
    public final static String NOT_DELETE_USER_HAS_LOANS = "In order to delete the user, the loans process must be terminated.";
    public final static String CANT_PROCESS__WITH_BUILT_IN_TRUE_USER="Can't process about this user";
    public final static String PASSWORD_DOESNT_MATCH= "Password doesn't match";
    public final static String STAFF_DOESNT_PROCESS_ABOUT_ADMIN="Staff doesn't process about Administrator";
    public final static String STAFF_DOESNT_PROCESS_ABOUT_OTHER_STAFF="Only Administrator user process about staff user";
    public final static String IMAGE_NOT_FOUND_MESSAGE="ImageFile with id %s not found";

    /* CATEGORY EXCEPTION MESSAGES */
    public final static String CATEGORY_NOT_FOUND_MESSAGE="Category with id %d not found";
    public final static String CATEGORY_NOT_DELETE_MESSAGE="Category has book, Category id %d ";

    public final static String CATEGORY_NOT_DELETE_BUILTIN_MESSAGE="Category has builtIn=true not delete id %d ";

    /* PUBLISHER EXCEPTION MESSAGES */
    public final static String PUBLISHER_NOT_FOUND_MESSAGE="Publisher with id %d not found";
    public final static String PUBLISHER_NOT_DELETE_MESSAGE="Publisher has book publisher id %d";
    public final static String PUBLISHER_BUILTIN_TRUE_DELETE_MESSAGE="Could not delete %d id Publisher because of builtIn is true";
    public final static String PUBLISHER_BUILTIN_TRUE_UPDATE_MESSAGE="Could not update %d id Publisher because of builtIn is true";


    /* AUTHOR EXCEPTION MESSAGES */
    public final static String AUTHOR_NOT_FOUND_MESSAGE="Author with id %d not found";
    public final static String AUTHOR_NOT_DELETE_MESSAGE="Author has book Author id %d";
    public final static String AUTHOR_BUILTIN_TRUE_DELETE_MESSAGE="Could not delete %d id Author because of builtIn is true";
    public final static String AUTHOR_BUILTIN_TRUE_UPDATE_MESSAGE="Could not update %d id Author because of builtIn is true";


}
