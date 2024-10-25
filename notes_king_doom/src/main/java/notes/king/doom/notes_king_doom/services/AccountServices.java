package notes.king.doom.notes_king_doom.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import notes.king.doom.notes_king_doom.models.Account;
import notes.king.doom.notes_king_doom.repo.AccountRepo;

@Component
public class AccountServices {

    @Autowired
    private AccountRepo repo;
    public void report()
    {

    }
    public boolean  isValidSignUp(Account account,BindingResult result)
    {

        if(!result.hasErrors())
        {
            if(isValidPassword(account,result))
                if(isValidEmail(account,result))
                return true;
        }
        return false;
    }

    private boolean isValidEmail(Account account, BindingResult result) {
        if(repo.findByEmail(account.getEmail()) != null)
        {
            result.rejectValue("email", "error.emailexsit", "email already exsit choose somthing else");
            return false;
        }
        return true;
    }
    private boolean isValidPassword(Account account, BindingResult result) {
        String password = account.getPassword();
        haslowerCase(password, result);
        hasUpperCase(password, result);
        hasnumbers(password, result);
        hasSymbols(password, result);
        
        return !result.hasErrors();
    }
    private void hasSymbols(String password,BindingResult result) {
        String symbols = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/";
        int min = 1;
        int counter =  countBySymbol(symbols, password);
        if(counter < min)
        result.rejectValue("password", "error.password", "Password must contain at least one special character.");
    }

    private void hasnumbers(String password,BindingResult result) {
        int min = 3;
        String symbols = "1234567890";
        int counter = countBySymbol(symbols, password);
        if(counter < min)
        result.rejectValue("password", "error.password", "Password must contain at 3 one number.");
    }

    private void haslowerCase(String password,BindingResult result) {
        int counter = 0;
        int min = 3;
        for(int i = 0; i < password.length(); i++)
            if(Character.isLowerCase(password.charAt(i)))
                counter++;
        if(counter < min)
        result.rejectValue("password", "error.password", "Password must contain at least one lowercase letter.");    }

    private void hasUpperCase(String password,BindingResult result) {
        int counter = 0;
        int min = 3;
        for(int i = 0; i < password.length(); i++)
            if(Character.isUpperCase(password.charAt(i)))
                counter++;
        if(counter < min)
        result.rejectValue("password", "error.password", "Password must contain at least three uppercase letter.");

    }
    private int  countBySymbol(String message,String text)
    {
        int counter = 0;
        String symbols = message;
        for(int i = 0; i < text.length(); i++)
            if(symbols.indexOf(text.charAt(i)) != -1)
                counter++;
        return counter;
    }

    public boolean  signUp()
    {
        return true;
    }
    /**
     * 
     * @param account
     * @param result
     * @return the account if found or no errors found return null if any erros found
     * @implNote this method check if the email found in the database or not and check wither the password match or not before returing null it also reutrn null if any error other than this happend like the email or password fields are empty
     * @implSpec we prefare to combine the not found email and passowrd not matching both for more scuirty porposes
     * @see BindingResult
     * @see Account
     */
    public Account isValidLogin(Account account,BindingResult result) {
        Account tempAcount;
        if(!result.hasErrors())
        {
            tempAcount = repo.findByEmail(account.getEmail());
            if(tempAcount != null && check_pass(tempAcount.getPassword(), account.getPassword()))
                return tempAcount;
            else
            result.reject("email.exists_passowrd.missmatch", "An account with this email already exists or the password is wrong");
                
        }
        return null;
    }

    private boolean  check_pass(String passowrd1,String password2)
    {
        return (passowrd1 == null ? password2 == null : passowrd1.equals(password2));
    }  
}