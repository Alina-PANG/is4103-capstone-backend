package capstone.is4103capstone.admin.service;

import capstone.is4103capstone.admin.repository.FunctionRepository;
import capstone.is4103capstone.entities.CompanyFunction;
import capstone.is4103capstone.entities.Region;
import capstone.is4103capstone.util.exception.CompanyFunctionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyFunctionService {
    @Autowired
    private FunctionRepository functionRepository;

    public CompanyFunction retrieveCompanyFunctionById(String functionId) throws CompanyFunctionNotFoundException {
        if (functionId == null || functionId.trim().length() == 0) {
            throw new CompanyFunctionNotFoundException("Invalid company ID ID given!");
        }
        functionId = functionId.trim();
        Optional<CompanyFunction> optionalCompanyFunction = functionRepository.findById(functionId);
        if (!optionalCompanyFunction.isPresent()) {
            throw new CompanyFunctionNotFoundException("Employee with ID " + functionId + " does not exist!");
        }

        return optionalCompanyFunction.get();
    }

    public Boolean validateFunctionId(String id){
        Optional<CompanyFunction> optionalFunction = functionRepository.findById(id);
        if (!optionalFunction.isPresent()) {
            return false;
        }else{
            return true;
        }
    }
}
