package project.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import project.service.UserService;

@RequiredArgsConstructor
public class AccountValidator implements ConstraintValidator<ExitUser, Object> {
    private final UserService usersService;
    String column;
    String dbColumn;

    @Override
    public void initialize(ExitUser constraintAnnotation) {
        this.column = constraintAnnotation.column();
        this.dbColumn = constraintAnnotation.dbColumn();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String columnValue = (String) new BeanWrapperImpl(value).getPropertyValue(column);
        return switch (dbColumn) {
            case "id" -> !usersService.existsByUsername(columnValue);
            case "email" -> !usersService.existsByEmail(columnValue);
            case "mobile" -> !usersService.existsByMobile(columnValue);
            default -> true;
        };
    }
}
