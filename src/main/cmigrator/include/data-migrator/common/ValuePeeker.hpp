#ifndef VALUEPEEKER_HPP_
#define VALUEPEEKER_HPP_

/**
   This is only a hookup (or mockup) that we use to be able to mirror our code in S-Store database 
   and avoid any errors during compilation as well as test our solutions separately.
 */
#include "dataMigratorExceptions.h"
#include "NValue.hpp"

namespace voltdb {
    class ValuePeeker {
    public:
	inline int getInteger(const NValue) const { throw DataMigratorNotImplementedException(); }
	inline std::string getString(const NValue&) const { throw DataMigratorNotImplementedException(); }
    };
}

#endif /* VALUEPEEKER_HPP_ */
