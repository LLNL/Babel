#
# File:  sidlBaseException.py
# Copyright (c) 2005 Lawrence Livermore National Security, LLC
# $Revision$
# $Date$
#

class sidlBaseException(Exception):
    """Base class for all SIDL Exception classes"""

    def __init__(self, exception):
        self.exception = exception
