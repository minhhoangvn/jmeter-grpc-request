package vn.zalopay.benchmark;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.protobuf.Descriptors;

import kg.apc.jmeter.JMeterPluginsUtils;
import kg.apc.jmeter.gui.BrowseAction;
import kg.apc.jmeter.gui.GuiBuilderHelper;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.JSyntaxTextArea;
import org.apache.jmeter.gui.util.JTextScrollPane;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.zalopay.benchmark.core.ClientList;
import vn.zalopay.benchmark.core.protobuf.ProtoMethodName;
import vn.zalopay.benchmark.core.protobuf.ServiceResolver;
import vn.zalopay.benchmark.util.JMeterVariableUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class GRPCSamplerGui extends AbstractSamplerGui {

    private static final Logger log = LoggerFactory.getLogger(GRPCSamplerGui.class);
    private static final long serialVersionUID = 240L;

    private static final String WIKI_PAGE = "https://github.com/zalopay-oss/jmeter-grpc-request";
    private static final String GOOGLE_PROTOBUF_PACKAGE_PREFIX = "google.protobuf";
    private static final String GOOGLE_PROTOBUF_DEFAULT_KEY = "value";

    private GRPCSampler grpcSampler;
    private String[] protoMethods;

    private JTextField protoFolderField;
    private JButton protoBrowseButton;

    private JTextField libFolderField;
    private JButton libBrowseButton;

    private JComboBox<String> fullMethodField;
    private JButton fullMethodButton;

    private JTextField metadataField;
    private JLabeledTextField hostField;
    private JLabeledTextField portField;
    private JLabeledTextField deadlineField;
    private JLabeledTextField channelFactoryShutdownTimeField;
    private JLabeledTextField maxInboundMessageSize;
    private JLabeledTextField maxInboundMetadataSize;

    private JCheckBox isTLSCheckBox;
    private JCheckBox isTLSDisableVerificationCheckBox;

    private JSyntaxTextArea requestJsonArea;

    public GRPCSamplerGui() {
        super();
        initGui();
        initGuiValues();
    }

    @Override
    public String getLabelResource() {
        return "grpc_sampler_title"; // $NON-NLS-1$
    }

    @Override
    public String getStaticLabel() {
        return "GRPC Request";
    }

    @Override
    public TestElement createTestElement() {
        grpcSampler = new GRPCSampler();
        modifyTestElement(grpcSampler);
        return grpcSampler;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        if (!(element instanceof GRPCSampler)) {
            return;
        }
        grpcSampler = (GRPCSampler) element;
        grpcSampler.setProtoFolder(this.protoFolderField.getText());
        grpcSampler.setLibFolder(this.libFolderField.getText());
        grpcSampler.setMetadata(this.metadataField.getText());
        grpcSampler.setHost(this.hostField.getText());
        grpcSampler.setPort(this.portField.getText());
        grpcSampler.setFullMethod(this.fullMethodField.getSelectedItem().toString());
        grpcSampler.setDeadline(this.deadlineField.getText());
        grpcSampler.setTls(this.isTLSCheckBox.isSelected());
        grpcSampler.setTlsDisableVerification(this.isTLSDisableVerificationCheckBox.isSelected());
        grpcSampler.setChannelShutdownAwaitTime(this.channelFactoryShutdownTimeField.getText());
        grpcSampler.setChannelMaxInboundMessageSize(this.maxInboundMessageSize.getText());
        grpcSampler.setChannelMaxInboundMetadataSize(this.maxInboundMetadataSize.getText());
        grpcSampler.setRequestJson(this.requestJsonArea.getText());
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (!(element instanceof GRPCSampler)) {
            return;
        }
        grpcSampler = (GRPCSampler) element;
        protoFolderField.setText(grpcSampler.getProtoFolder());
        libFolderField.setText(grpcSampler.getLibFolder());
        metadataField.setText(grpcSampler.getMetadata());
        hostField.setText(grpcSampler.getHost());
        portField.setText(grpcSampler.getPort());
        fullMethodField.setSelectedItem(grpcSampler.getFullMethod());
        deadlineField.setText(grpcSampler.getDeadline());
        isTLSCheckBox.setSelected(grpcSampler.isTls());
        isTLSDisableVerificationCheckBox.setSelected(grpcSampler.isTlsDisableVerification());
        channelFactoryShutdownTimeField.setText(
                Integer.toString(grpcSampler.getChannelShutdownAwaitTime()));
        maxInboundMessageSize.setText(
                Integer.toString(grpcSampler.getChannelMaxInboundMessageSize()));
        maxInboundMetadataSize.setText(
                Integer.toString(grpcSampler.getChannelMaxInboundMetadataSize()));
        requestJsonArea.setText(grpcSampler.getRequestJson());
    }

    @Override
    public void clearGui() {
        super.clearGui();
        initGuiValues();
    }

    private void initGuiValues() {
        protoFolderField.setText("");
        libFolderField.setText("");
        metadataField.setText("");
        hostField.setText("");
        portField.setText("");
        fullMethodField.setSelectedItem("");
        deadlineField.setText("1000");
        isTLSCheckBox.setSelected(false);
        isTLSDisableVerificationCheckBox.setSelected(false);
        channelFactoryShutdownTimeField.setText("1000");
        maxInboundMessageSize.setText("4194304");
        maxInboundMetadataSize.setText("8192");
        requestJsonArea.setText("");
    }

    private void initGui() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        // TOP panel
        Container topPanel = makeTitlePanel();
        add(JMeterPluginsUtils.addHelpLinkToPanel(topPanel, WIKI_PAGE), BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);

        // MAIN panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(getWebServerPanel());
        mainPanel.add(getGRPCRequestPanel());
        mainPanel.add(getOptionConfigPanel());
        mainPanel.add(getRequestJSONPanel());
        add(mainPanel, BorderLayout.CENTER);
    }

    /** Helper function */
    private void addToPanel(
            JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
        constraints.gridx = col;
        constraints.gridy = row;
        panel.add(component, constraints);
    }

    private JPanel getWebServerPanel() {
        portField = new JLabeledTextField("Port Number:", 3); // $NON-NLS-1$
        hostField = new JLabeledTextField("Server Name or IP:", 11); // $NON-NLS-1$
        isTLSCheckBox = new JCheckBox("SSL/TLS");
        isTLSDisableVerificationCheckBox = new JCheckBox("Disable SSL/TLS Cert Verification");
        JPanel webServerPanel = new VerticalPanel();
        webServerPanel.setBorder(BorderFactory.createTitledBorder("Web Server")); // $NON-NLS-1$

        JPanel webserverHostPanel = new HorizontalPanel();
        webserverHostPanel.add(hostField);
        webserverHostPanel.add(portField);

        JPanel webserverOtherPanel = new HorizontalPanel();
        webserverOtherPanel.add(isTLSCheckBox);
        webserverOtherPanel.add(isTLSDisableVerificationCheckBox);
        webServerPanel.add(webserverHostPanel);
        webServerPanel.add(webserverOtherPanel);
        return webServerPanel;
    }

    private JPanel getRequestJSONPanel() {
        requestJsonArea = JSyntaxTextArea.getInstance(30, 50);
        requestJsonArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        requestJsonArea.setBracketMatchingEnabled(true);
        requestJsonArea.setPaintMatchedBracketPair(true);
        requestJsonArea.setAutoIndentEnabled(true);
        requestJsonArea.setMarkOccurrences(true);
        requestJsonArea.setPaintMarkOccurrencesBorder(true);
        requestJsonArea.setPaintTabLines(true);
        requestJsonArea.setShowMatchedBracketPopup(true);

        JPanel webServerPanel = new JPanel(new BorderLayout());
        webServerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(9, 0, 0, 0),
                        BorderFactory.createTitledBorder("Send JSON Format With the Request")));
        JTextScrollPane syntaxPanel = JTextScrollPane.getInstance(requestJsonArea);
        webServerPanel.add(syntaxPanel);
        return webServerPanel;
    }

    private JPanel getOptionConfigPanel() {
        JPanel optionalPanel = new VerticalPanel();
        optionalPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(9, 0, 0, 0),
                        BorderFactory.createTitledBorder("Optional Configuration")));

        JLabel metadataLabel = new JLabel("Metadata:");
        metadataField = new JTextField("Metadata", 32); // $NON-NLS-1$
        deadlineField = new JLabeledTextField("Deadline In Millisecond:", 7); // $NON-NLS-1$
        channelFactoryShutdownTimeField =
                new JLabeledTextField("Channel Await Termination In Millisecond:", 5);
        maxInboundMessageSize =
                new JLabeledTextField("Maximum message size allowed for a single gRPC frame");
        maxInboundMetadataSize =
                new JLabeledTextField("Maximum size of metadata allowed to be received:");
        JPanel metadataServerPanel = new HorizontalPanel();

        metadataServerPanel.add(metadataLabel);
        metadataServerPanel.add(metadataField);

        JPanel timeOutOptionServerPanel = new HorizontalPanel();
        timeOutOptionServerPanel.add(deadlineField);
        timeOutOptionServerPanel.add(channelFactoryShutdownTimeField);

        optionalPanel.add(metadataServerPanel);
        optionalPanel.add(timeOutOptionServerPanel);
        optionalPanel.add(maxInboundMessageSize);
        optionalPanel.add(maxInboundMetadataSize);
        return optionalPanel;
    }

    private JPanel getGRPCRequestPanel() {
        JPanel requestPanel = new JPanel(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;

        GridBagConstraints editConstraints = new GridBagConstraints();
        editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        editConstraints.weightx = 1.0;
        editConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Proto folder
        int row = 0;
        addToPanel(
                requestPanel,
                labelConstraints,
                0,
                row,
                new JLabel("Proto Root Directory: ", JLabel.RIGHT));
        addToPanel(requestPanel, editConstraints, 1, row, protoFolderField = new JTextField(20));
        addToPanel(
                requestPanel,
                labelConstraints,
                2,
                row,
                protoBrowseButton = new JButton("Browse..."));
        row++;
        GuiBuilderHelper.strechItemToComponent(protoFolderField, protoBrowseButton);

        editConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        labelConstraints.insets = new java.awt.Insets(2, 0, 0, 0);

        protoBrowseButton.addActionListener(new BrowseAction(protoFolderField, true));

        // Lib folder
        addToPanel(
                requestPanel,
                labelConstraints,
                0,
                row,
                new JLabel("Library Directory (Optional): ", JLabel.RIGHT));
        addToPanel(requestPanel, editConstraints, 1, row, libFolderField = new JTextField(20));
        addToPanel(
                requestPanel, labelConstraints, 2, row, libBrowseButton = new JButton("Browse..."));
        row++;
        GuiBuilderHelper.strechItemToComponent(libFolderField, libBrowseButton);

        editConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        labelConstraints.insets = new java.awt.Insets(2, 0, 0, 0);

        libBrowseButton.addActionListener(new BrowseAction(libFolderField, true));

        // Full method
        addToPanel(
                requestPanel, labelConstraints, 0, row, new JLabel("Full Method: ", JLabel.RIGHT));
        addToPanel(requestPanel, editConstraints, 1, row, fullMethodField = new JComboBox<>());
        fullMethodField.setEditable(true);
        fullMethodField.setMaximumRowCount(12);
        addToPanel(
                requestPanel,
                labelConstraints,
                2,
                row,
                fullMethodButton = new JButton("Listing..."));

        // fullMethodButton click listener
        registerListGRPCMethodListener();

        // Container
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(9, 0, 0, 0),
                        BorderFactory.createTitledBorder("GRPC Request")));
        container.add(requestPanel, BorderLayout.NORTH);
        return container;
    }

    private void reloadProtoMethods(boolean reload) {
        getProtoMethods(reload);
        String item = getFullMethodName();
        fullMethodField.setSelectedItem(item);
        fullMethodField.showPopup();
    }

    private String[] getProtoMethods(boolean reload) {
        try {
            JMeterVariableUtils.undoVariableReplacement(grpcSampler);
            ServiceResolver serviceResolver =
                    ClientList.getServiceResolver(
                            grpcSampler.getProtoFolder(), grpcSampler.getLibFolder(), reload);
            List<String> methodList = ClientList.listServices(serviceResolver);
            protoMethods = new String[methodList.size()];
            methodList.toArray(protoMethods);
            Arrays.sort(protoMethods);
            fullMethodField.setModel(new DefaultComboBoxModel<>(protoMethods));
            log.info("Full Methods Length: {}", protoMethods.length);
            return protoMethods;
        } catch (Exception e) {
            log.error("Proto folder path is empty. Please select your proto folder", e);
            throw e;
        }
    }

    private void generateGRPCRequestMockData() {
        try {
            if (StringUtils.isNotBlank(requestJsonArea.getText())) {
                return;
            }
            String fullMethod = getFullMethodName();
            ProtoMethodName grpcMethodName = ProtoMethodName.parseFullGrpcMethodName(fullMethod);
            JMeterVariableUtils.undoVariableReplacement(grpcSampler);
            ServiceResolver serviceResolver =
                    ClientList.getServiceResolver(
                            grpcSampler.getProtoFolder(), grpcSampler.getLibFolder());
            Descriptors.MethodDescriptor methodDescriptor =
                    serviceResolver.resolveServiceMethod(grpcMethodName);
            Descriptors.Descriptor inputType = methodDescriptor.getInputType();
            List<Descriptors.FieldDescriptor> fields = inputType.getFields();
            JSONObject requestBody = new JSONObject(true);
            for (Descriptors.FieldDescriptor field : fields) {
                String name = field.getName();
                Object defaultValue = getMockValue(field);
                requestBody.put(name, defaultValue);
            }

            String text = "";
            if (inputType.getFullName().startsWith(GOOGLE_PROTOBUF_PACKAGE_PREFIX)) {
                text = requestBody.getString(GOOGLE_PROTOBUF_DEFAULT_KEY);
            } else {
                text =
                        requestBody.toString(
                                SerializerFeature.PrettyFormat, // Formatting Json String
                                SerializerFeature.WriteMapNullValue, // Outputs Null values
                                SerializerFeature.WriteNullListAsEmpty // Null List output is []
                                );
            }
            requestJsonArea.setText(text);
        } catch (Exception ex) {
            log.error("request mock error", ex);
        }
    }

    private Object getMockValue(Descriptors.FieldDescriptor field) {
        String name = field.getName();
        String type = field.getType().name().toLowerCase();
        if ("message".equals(type)) {
            List<Descriptors.FieldDescriptor> fields = field.getMessageType().getFields();
            JSONObject repeatedField = new JSONObject(true);
            for (Descriptors.FieldDescriptor repeatedFieldDescriptor : fields) {
                repeatedField.put(
                        repeatedFieldDescriptor.getName(),
                        this.getMockValue(repeatedFieldDescriptor));
            }

            if (field.isRepeated()) {
                return new JSONArray().fluentAdd(repeatedField);
            }

            return repeatedField;
        } else {
            return getMockDefaultValue(name, type);
        }
    }

    private Object getMockDefaultValue(String name, String type) {
        switch (type) {
            case "string":
                return fieldNameGenerateMock(name);
            case "bool":
                return true;
            case "number":
            case "int32":
                return 10;
            case "int64":
                return 20;
            case "uint32":
            case "uint64":
            case "sint32":
                return 100;
            case "sint64":
                return 1200;
            case "fixed32":
                return 1400;
            case "fixed64":
                return 1500;
            case "sfixed32":
                return 1600;
            case "sfixed64":
                return 1700;
            case "float":
                return 1.1;
            case "double":
                return 1.4;
            case "bytes":
                return "Hello";
            default:
                return null;
        }
    }

    /**
     * Mock generation from fieldName.
     *
     * <p>Default: Hello
     */
    private String fieldNameGenerateMock(String fieldName) {
        String fieldNameLower = fieldName.toLowerCase();

        if (fieldNameLower.startsWith("id") || fieldNameLower.endsWith("id")) {
            return UUID.randomUUID().toString();
        }

        return "Hello";
    }

    private String getFullMethodName() {
        Object methodName = fullMethodField.getSelectedItem();
        if (methodName == null) return "";
        return methodName.toString();
    }

    private void registerListGRPCMethodListener() {
        registerListGRPCMethods();
        registerGenerateGRPCRequestMockData();
        registerAutoCompleteListGRPCMethods();
    }

    private void registerListGRPCMethods() {
        // fullMethodButton click listener
        fullMethodButton.addActionListener(
                e -> {
                    if (grpcSampler.getProtoFolder().equals(protoFolderField.getText())
                            && grpcSampler.getLibFolder().equals(libFolderField.getText())) {
                        reloadProtoMethods(false);
                    } else {
                        grpcSampler.setProtoFolder(protoFolderField.getText());
                        grpcSampler.setLibFolder(libFolderField.getText());
                        reloadProtoMethods(true);
                    }
                });
    }

    private void registerGenerateGRPCRequestMockData() {
        fullMethodField.addPopupMenuListener(
                new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

                    // fullMethod list checked listener
                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                        // Request Mock Auto Create
                        generateGRPCRequestMockData();
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {}
                });
    }

    private void registerAutoCompleteListGRPCMethods() {
        // fullMethod edit enter listener
        fullMethodField.addActionListener(
                e -> {
                    if ("comboBoxEdited".equals(e.getActionCommand())) {
                        // fullMethodField Drop - down box edit auto-complement
                        String fullMethod = getFullMethodName();
                        if (StringUtils.isBlank(fullMethod)) {
                            return;
                        }

                        try {
                            String[] protoMethods = getProtoMethods(true);
                            for (String protoMethod : protoMethods) {
                                boolean startsWith = protoMethod.startsWith(fullMethod);
                                if (startsWith) {
                                    fullMethodField.setSelectedItem(protoMethod);
                                    if (!protoMethod.equals(fullMethod)) {
                                        fullMethodField.showPopup();
                                    }

                                    break;
                                }
                            }
                        } catch (Exception ex) {
                            log.error("Error in reload service name {}", e);
                        }
                        // Request Mock Auto Create
                        generateGRPCRequestMockData();
                    }
                });
    }
}
