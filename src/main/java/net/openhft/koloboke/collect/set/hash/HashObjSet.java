/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.koloboke.collect.set.hash;

import net.openhft.koloboke.collect.hash.HashContainer;
import net.openhft.koloboke.collect.set.ObjSet;


/**
 * An interface for {@code ObjSet}s, based on hash tables.
 *
 * <p>This interface doesn't carry own specific behaviour, just combines it's superinterfaces.
 *
 * <p>Looking for a way to instantiate a {@code HashObjSet}? See static factory methods
 * in {@link HashObjSets} class.
 *
 * @see HashObjSets
 * @see HashObjSetFactory
 */
public interface HashObjSet<E> extends ObjSet<E>, HashContainer {
}

