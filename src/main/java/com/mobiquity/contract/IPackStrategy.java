package com.mobiquity.contract;

import com.mobiquity.model.Package;

/**
 * Contract to be implemented by different packing strategies following strategy design pattern
 */
public interface IPackStrategy {
  Package pack(Package p);
}
