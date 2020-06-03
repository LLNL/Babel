##
## File:        Makefile.am 
## Package:     Babel makefiles
## Copyright:   (c) 2000-2001 Lawrence Livermore National Security, LLC
## Revision:    $Revision: 7419 $
## Modified:    $Date: 2011-12-15 09:39:29 -0800 (Thu, 15 Dec 2011) $
## Description: automake makefile to build the Babel documentation
##




SUBDIRS = html manuals

EXTRA_DIST = babel.txt
dist_man_MANS = $(srcdir)/babel.1

# We are distributing the compiled manpage so we don't depend on rst2man
# We need to trick rst2man to recognize long options as options, too
$(srcdir)/babel.1: babel.txt Makefile
	if which rst2man >/dev/null; then \
		sed 's/^  --/  -, --/g' $< >$<.tmp; \
		rst2man $<.tmp $@.tmp; \
		sed 's/\\-, \\-/\\-/g' $@.tmp >$@; \
                rm -f $<.tmp $@.tmp; \
	else \
		echo "**WARNING rst2man not installed, did not update $@"; \
	fi
