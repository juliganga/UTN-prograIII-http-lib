package org.juliganga.Model.MenuModel;

import org.json.JSONObject;
import org.juliganga.Exceptions.NonExistantOptionException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** <h1>Esto está abandonado.</h1>
 *
 * @deprecated
 * Esto está abandonado. No se hará un frontend en este programa.
 *
 * */
@Deprecated
public class UpdateMenu {
    private Map<Integer, MenuOption> menuOptionMap;
    private JSONObject jsonObject;

    @Deprecated
    public UpdateMenu(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.menuOptionMap = createMenuHashMap(jsonObject);
    }

    private Map<Integer, MenuOption> createMenuHashMap(JSONObject jsonObject) {

        Iterator<String> keys = jsonObject.keys();
        Map<Integer, MenuOption> optionMap = new HashMap<>();

        int id = 1;
        while (keys.hasNext()) {
            String parent_name = keys.next();

            if (jsonObject.get(parent_name) instanceof JSONObject) {
                Iterator<String> object_keys = ((JSONObject) jsonObject.get(parent_name)).keys();

                while (object_keys.hasNext()) {
                    String object_key = object_keys.next();
                    System.out.println(object_key);
                    JSONObject parent_object = jsonObject.getJSONObject(parent_name);


                    MenuOption option = new MenuOption(parent_name, object_key, (parent_object.get(object_key).getClass()));
                    optionMap.put(id, option);
                    id++;
                }
            } else {
                System.out.println(parent_name);

                MenuOption option = new MenuOption(parent_name, jsonObject.get(parent_name).getClass());
                optionMap.put(id, option);
                id++;
            }
        }


        optionMap = Collections.unmodifiableMap(optionMap);
        return optionMap;
    }


    public String showOptions() {
        String data = "";
        for (Map.Entry<Integer, MenuOption> option : menuOptionMap.entrySet()) {
            data = data.concat(option.getKey() + "- Modificar " + option.getValue().getOption() + "\n");
        }

        return data;
    }

    private void checkIfOptionExists(int id) throws NonExistantOptionException {
        if(!menuOptionMap.containsKey(id))
        {
            throw new NonExistantOptionException("Esa opcion no existe.");
        }
    }

    public void updateValue(int id, int value) throws NonExistantOptionException
    {
        checkIfOptionExists(id);

        if(checkInputClass(value,getOptionType(id)))
        {
            MenuOption option = menuOptionMap.get(id);

            if(option.getParent() == null)
            {
                jsonObject.put(option.getOption(),value);
            } else {
                JSONObject found_object = (JSONObject) jsonObject.get(option.getParent());
                found_object.put(menuOptionMap.get(id).getOption(),value);
            }
        } else {
            System.out.println("Tipo de dato no apropiado.");
        }


    }

    public void updateValue(int id, String value) throws NonExistantOptionException
    {
        checkIfOptionExists(id);

        if(checkInputClass(value,getOptionType(id)))
        {
            MenuOption option = menuOptionMap.get(id);

            if(option.getParent() == null)
            {
                jsonObject.put(option.getOption(),value);
            } else {
                JSONObject found_object = (JSONObject) jsonObject.get(option.getParent());
                found_object.put(getMenuOptionObject(id).getOption(),value);
            }
        } else {
            System.out.println("Tipo de dato no apropiado.");
        }

    }

    public String getJsonObject() {
        return jsonObject.toString();
    }

    public boolean checkInputClass(Object object, Class input_class)
    {
        return input_class == object.getClass();
    }

    public Class getOptionType(int id) throws NonExistantOptionException
    {
        return getMenuOptionObject(id).getType();
    }

    private MenuOption getMenuOptionObject(int id) throws NonExistantOptionException
    {
        checkIfOptionExists(id);
        return menuOptionMap.get(id);
    }

}
