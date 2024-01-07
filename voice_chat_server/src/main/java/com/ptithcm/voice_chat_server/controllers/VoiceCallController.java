/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.voice_chat_server.controllers;

import com.ptithcm.voice_chat_server.annotations.Body;
import com.ptithcm.voice_chat_server.annotations.Controller;
import com.ptithcm.voice_chat_server.annotations.RequestMapping;
import com.ptithcm.voice_chat_server.enums.ResponseStatus;
import com.ptithcm.voice_chat_server.enums.VoiceCallStatus;
import com.ptithcm.voice_chat_server.models.ClientInfo;
import com.ptithcm.voice_chat_server.models.Packet;
import com.ptithcm.voice_chat_server.models.Response;
import com.ptithcm.voice_chat_server.models.TCPClientSocket;
import com.ptithcm.voice_chat_server.models.VoiceCall;
import com.ptithcm.voice_chat_server.store.SocketStore;
import java.util.Date;

/**
 *
 * @author minhd
 */
@Controller
public class VoiceCallController {

    @RequestMapping(header = "Create-Single-Voice-Call")
    public void createVoiceCall(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
        voiceCall.setStartTime(new Date());
        if (isAccessibleCall(voiceCall)) {
            voiceCall.setStatus(VoiceCallStatus.RINGING.getValue());
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().createSingleVoiceCall(voiceCall);
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().createSingleVoiceCall(voiceCall);

//            Send a notification to the caller
            tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);

//            Send a notification to the call recipient
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Incomming-Call", voiceCall)), true);
        } else {
            voiceCall.setStatus(VoiceCallStatus.BUSY.getValue());
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getTo().getUuid(), voiceCall);
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().endSingleVoiceCall();
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getFrom().getUuid(), voiceCall);
            SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().endSingleVoiceCall();

//            Send a notification to the caller
            tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
        }
    }

    @RequestMapping(header = "Not-Answering")
    public void notAnswering(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
//        Record call history
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getTo().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().endSingleVoiceCall();
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getFrom().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().endSingleVoiceCall();

//        Send a notification to the caller
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
    }

    @RequestMapping(header = "Accept-Call")
    public void acceptCall(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
//        Send a notification to the caller
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
    }

    @RequestMapping(header = "Get-Current-Call")
    public void getCurrentCall(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Get-Current-Call", voiceCall)), true);
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
    }

    @RequestMapping(header = "Cancel-Call")
    public void cancelCall(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
//        Record call history
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getTo().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().endSingleVoiceCall();
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getFrom().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().endSingleVoiceCall();

//        Send a notification to the caller
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
    }

    @RequestMapping(header = "End-Call")
    public void endCall(@Body VoiceCall voiceCall, TCPClientSocket tcpcs) {
//        Record call history
        voiceCall.setEndTime(new Date());
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getTo().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).getVoiceCallManager().endSingleVoiceCall();
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().recordCallHistory(voiceCall.getFrom().getUuid(), voiceCall);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().endSingleVoiceCall();

//        Send a notification to the caller
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getFrom().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Single-Voice-Call", voiceCall)), true);
    }

    @RequestMapping(header = "Microphone-Status")
    public void updateMicrophoneStatus(@Body Boolean isOpeningMicrophone, TCPClientSocket tcpcs) {
        tcpcs.send(new Response(ResponseStatus.OK.getValue(), new Packet("Microphone-Status", isOpeningMicrophone)), true);
        ClientInfo receiver = tcpcs.getVoiceCallManager().getCurrentCall().getFrom().getUuid().equals(tcpcs.getClientInfo().getUuid()) ? tcpcs.getVoiceCallManager().getCurrentCall().getTo() : tcpcs.getClientInfo();
        SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(receiver.getUuid()).send(new Response(ResponseStatus.OK.getValue(), new Packet("Update-Microphone-Status", isOpeningMicrophone)), true);
    }

    public boolean isAccessibleCall(VoiceCall voiceCall) {
        if (SocketStore.tcpServerSocket.getTcpClientSocketManager().getClientSocketMap().get(voiceCall.getTo().getUuid()).getVoiceCallManager().getCurrentCall() == null) {
            return true;
        } else {
            return false;
        }
    }
}
