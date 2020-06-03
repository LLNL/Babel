;;
;; Sample .emacs for Babel development
;;
(display-time)
(setq display-time-day-and-date t)

; (setq author-name "User Name")
; (setq author-email-addr "user@host")

(defun java-header-author-name() 
  (if (boundp 'author-name)
      (if (boundp 'author-email-addr)
	  (concat author-name " <" author-email-addr ">")
	author-name)
    (user-full-name)))

(defun java-file-header ()
  (interactive)
  (goto-char (point-min))
  (insert (format "\
//\n\
// File:        %s\n\
// Package:     \n\
// Copyright:   (c) %s Lawrence Livermore National Security, LLC\n\
// Revision:    @(#) $Revision$\n\
// Date:        $Date$\n\
// Description: \n\
// \n" 
		  (buffer-name)
		  (substring (current-time-string) 20 24)))
; no author
;		  (java-header-author-name)))
  (goto-char (point-min))
  (end-of-line 3))

(add-hook 'java-mode-hook 
	  (function (lambda ()
		      (setq indent-tabs-mode nil)
;		      (setq compile-command "gmake")
		      (setq fill-column 76)
		      (auto-fill-mode 1)
		      (let ((offset-alist 
			     '((inline-open . 0)
			       (topmost-intro-cont    . +)
			       (statement-block-intro . +)
			       (knr-argdecl-intro     . 5)
			       (substatement-open     . +)
			       (label                 . 0)
			       (statement-case-open   . +)
			       (statement-cont        . +)
			       (arglist-intro 
				.
				c-lineup-arglist-intro-after-paren)
			       (arglist-close . c-lineup-arglist)
			       (access-label  . 0)))
			    (offset-item nil))
 			(and (fboundp 'c-lineup-java-inher)
 			     (setq offset-alist 
 				   (append offset-alist
 					   '((inher-cont 
 					      . c-lineup-java-inher)))))
 			(and (fboundp 'c-lineup-java-throws)
 			     (setq offset-alist
 				   (append offset-alist
 					   '((func-decl-cont
 					      . c-lineup-java-throws)))))
			(setq offset-item '(c-offsets-alist))
			(setcdr offset-item offset-alist)
			
			(c-add-style "babel"
				     (append
				      '(
					(c-basic-offset . 2)
					(c-comment-only-line-offset 
					 . (0 . 0)))
				      (list offset-item)) t))
		      (if (eq (buffer-size) 0) (java-file-header)))))
