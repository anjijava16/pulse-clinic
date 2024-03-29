/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.rest.logic;


import com.pulse.model.User;
import java.util.List;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public interface UserService {
    public User update(User user);
    
    public User delete(long id);

    public User get(long id);
    
    public List<User> list();
    
    public User find(String username);
    
    public User find(String username, String password);
}
