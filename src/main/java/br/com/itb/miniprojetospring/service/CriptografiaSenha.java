package br.com.itb.miniprojetospring.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class CriptografiaSenha {
	
	public String criptografarSenha(String senha) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(senha.getBytes());
		
		byte[] digest = md.digest();
		
		StringBuffer sb = new StringBuffer();
		
		for(byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
				
	}

}
