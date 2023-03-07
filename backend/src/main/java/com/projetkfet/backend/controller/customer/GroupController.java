package com.projetkfet.backend.controller.customer;


import com.projetkfet.backend.data.customer.GroupRepository;
import com.projetkfet.backend.model.customer.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path="/group")
public class GroupController {

    private static final Logger logger = LogManager.getLogger("CustomerLogger");

    @Autowired
    private GroupRepository groupRepository;

    //    GET

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Group> getAllGroups() {
        logger.info("All groups");
        return groupRepository.findAll();
    }

    //    POST

    @PostMapping(path="/add")
    public @ResponseBody
    UUID addNewGroup(@RequestParam("name") String name) throws Exception {
        logger.info("New group : " + name);

        UUID id = null;

        Group g = new Group();
        g.setName(name);

        groupRepository.save(g);

        Optional<Group> gr = groupRepository.findByName(name);

        if (gr.isPresent())
        {
            Group group = gr.get();
            id = group.getId();
            logger.info("Id Group : "+ id);
        }
        else
        {
            logger.info("Error create Group");
            throw new Exception("Error create Group");
        }

        return id;
    }

    //    UPDATE

    //    DELETE

    @DeleteMapping()
    public @ResponseBody
    void deleteUser(@RequestParam("id") String id) throws Exception {
        logger.info("Delete Group");
        Optional<Group> g = groupRepository.findById(UUID.fromString(id));

        if (g.isPresent())
        {
            logger.info("Group deleted : " + id);
            groupRepository.delete(g.get());
        }
        else
        {
            logger.info("No group for this ID");
            throw new Exception("No group for this ID");
        }

    }
}
