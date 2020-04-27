package br.com.empreendedorismo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountController accountController; 
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("UserController.findAll() - BEGIN");
		List<Usuario> listUser = new ArrayList<Usuario>();
		try {
			listUser = userService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.findAll() - END (" + endTime + "ms)");
		return listUser; 
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Integer id){
		long startTime = System.currentTimeMillis();
		log.info("UserController.findById() - BEGIN");
		ResponseEntity<Usuario> user = null;
		try {
			if (userService.findActive(id,null).equals(true)) {
				user = ResponseEntity.ok(userService.findById(id));
			}else {
				user = new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.findById() - END (" + endTime + "ms)");
		return user;
		
	}
	
	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody @Valid UserDTO userDTO, @Valid AccountDTO accountDTO) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("UserController.save(@RequestBody @Valid UserDTO userDTO, @Valid AccountDTO accountDTO) - BEGIN");
		ResponseEntity<Usuario> ret = null;
		try {
			if (userService.findActive(null,userDTO.getEmail()).equals(false)) {
				userService.save(userDTO, accountDTO);
				ret = new ResponseEntity<Usuario>(HttpStatus.CREATED);
			}else {
				ret = new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.save(@RequestBody @Valid UserDTO userDTO, @Valid AccountDTO accountDTO) - END (" + endTime + "ms)");
		return ret;
	}
	
	@PutMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Usuario update(@PathVariable Integer id, @RequestBody @Valid UserDTO dto){
		if(findById(id) != null) {
			return userService.update(id, dto);
		}
		return (Usuario) ResponseEntity.notFound();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Integer id) {
		userService.deleteById(id);
	}
	
	

}
