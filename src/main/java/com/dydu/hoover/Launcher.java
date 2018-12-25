package com.dydu.hoover;

import com.dydu.hoover.exceptions.InvalidRoomContentException;
import com.dydu.hoover.exceptions.InvalidRoomStructureException;
import com.dydu.hoover.model.Dyson;
import com.dydu.hoover.model.Room;
import com.dydu.hoover.utils.MatrixFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Launcher {

    private static final Logger LOG = LoggerFactory
            .getLogger(Launcher.class);

    public static void main(String[] args) {
        if (args.length == 1) {
            String fileName = args[0];
            try {
                Room room = new Room(new MatrixFileReader().readFile(fileName));
                room.roomCheck();
                Dyson hoover = new Dyson();
                long start = System.currentTimeMillis();
                hoover.clean(room);
                LOG.info("Room cleaning done, duration " + ((System.currentTimeMillis() - start)/1000) + " sec.");
                LOG.info("Moves " + hoover.getMoves());
            } catch (IOException e) {
                LOG.error("File not found, please check your file path ! ");
            } catch (InvalidRoomStructureException | InvalidRoomContentException e) {
                LOG.error(e.getMessage());
            }
        } else {
            LOG.error("Nothing to do, unless you specify a file with the room to clean...");
        }
    }
}
