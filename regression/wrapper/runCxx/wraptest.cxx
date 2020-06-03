#include <string>
#include <sstream>
#include <iostream>
using namespace std;
#include "wrapper_User.hxx"
#include "wrapper_Data.hxx"
#include "wrapper_Data_Impl.hxx"
#include "synch.hxx"

#include <string.h>

#ifdef SIDL_USE_UCXX
using namespace ucxx;
#endif /* UCXX */


#define MYASSERT( AAA )				\
  tracker.startPart(++part_no);			\
  tracker.writeComment(#AAA);			\
  if ( AAA ) result = synch::ResultType_PASS;	\
  else result = synch::ResultType_FAIL;		\
  tracker.endPart(part_no, result);

int main(int argc, char **argv) { 
  synch::ResultType result = synch::ResultType_PASS; 
  synch::RegOut tracker = synch::RegOut::_create();
  int part_no = 0;
  string language = "";
  tracker.setExpectations(-1);
  if (argc == 2) {
    language = argv[1];
  }
  wrapper::Data_impl *data = new wrapper::Data_impl();
  {
    wrapper::User user = wrapper::User::_create();
    wrapper::Data d_data = *data;

    MYASSERT( !data->_is_nil() );  
    MYASSERT( !user._is_nil() );

    MYASSERT( strcmp(data->d_ctorTest, "ctor was run") == 0 );
    
    /* Test the data setting*/
    user.accept(*data);
    
    MYASSERT( strcmp(data->d_string, "Hello World!") == 0 );
    MYASSERT( data->d_int == 3);
  }
  delete data;
  tracker.close();
  return 0;
}

