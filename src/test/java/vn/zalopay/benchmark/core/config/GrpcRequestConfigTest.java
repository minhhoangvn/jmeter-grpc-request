package vn.zalopay.benchmark.core.config;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import vn.zalopay.benchmark.core.specification.GrpcResponse;

public class GrpcRequestConfigTest {
    @Test
    public void canConvertConfigObjectToString() {
        GrpcRequestConfig grpcRequestConfig =
                new GrpcRequestConfig("a", "b", "c", "d", true, true, 1000);
        String grpcRequestConfigString = grpcRequestConfig.toString();
        Assert.assertEquals(
                grpcRequestConfigString,
                "GrpcRequestConfig{maxInboundMessageSize=4194304, maxInboundMetadataSize=8192,"
                    + " hostPort='a', testProtoFile='b', libFolder='c', fullMethod='d', tls=true,"
                    + " tlsDisableVerification=true, awaitTerminationTimeout=1000}");
    }

    @Test
    public void canGetDataWithBuilder() {
        SoftAssert softAssert = new SoftAssert();
        GrpcRequestConfig grpcRequestConfig =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        softAssert.assertEquals(grpcRequestConfig.getHostPort(), "a");
        softAssert.assertEquals(grpcRequestConfig.getProtoFolder(), "b");
        softAssert.assertEquals(grpcRequestConfig.getLibFolder(), "c");
        softAssert.assertEquals(grpcRequestConfig.getFullMethod(), "d");
        softAssert.assertEquals(grpcRequestConfig.isTls(), true);
        softAssert.assertEquals(grpcRequestConfig.isTlsDisableVerification(), true);
        softAssert.assertEquals(grpcRequestConfig.getAwaitTerminationTimeout(), 1000);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMetadataSize(), 1000);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMessageSize(), 1000);
        softAssert.assertAll();
    }

    @Test
    public void canGetDataWithDefault() {
        SoftAssert softAssert = new SoftAssert();
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        softAssert.assertEquals(grpcRequestConfig.getHostPort(), null);
        softAssert.assertEquals(grpcRequestConfig.getProtoFolder(), null);
        softAssert.assertEquals(grpcRequestConfig.getLibFolder(), null);
        softAssert.assertEquals(grpcRequestConfig.getFullMethod(), null);
        softAssert.assertEquals(grpcRequestConfig.isTls(), false);
        softAssert.assertEquals(grpcRequestConfig.isTlsDisableVerification(), false);
        softAssert.assertEquals(grpcRequestConfig.getAwaitTerminationTimeout(), 5000);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMetadataSize(), 8192);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMessageSize(), 4194304);
        softAssert.assertAll();
    }

    @Test
    public void canGetDataWithSetterNewValue() {
        SoftAssert softAssert = new SoftAssert();
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        grpcRequestConfig.setHostPort("a");
        grpcRequestConfig.setProtoFolder("b");
        grpcRequestConfig.setLibFolder("c");
        grpcRequestConfig.setFullMethod("d");
        grpcRequestConfig.setTls(true);
        grpcRequestConfig.setTlsDisableVerification(true);
        grpcRequestConfig.setAwaitTerminationTimeout(1000);
        grpcRequestConfig.setMaxInboundMessageSize(1000);
        grpcRequestConfig.setMaxInboundMetadataSize(1000);
        softAssert.assertEquals(grpcRequestConfig.getHostPort(), "a");
        softAssert.assertEquals(grpcRequestConfig.getProtoFolder(), "b");
        softAssert.assertEquals(grpcRequestConfig.getLibFolder(), "c");
        softAssert.assertEquals(grpcRequestConfig.getFullMethod(), "d");
        softAssert.assertEquals(grpcRequestConfig.isTls(), true);
        softAssert.assertEquals(grpcRequestConfig.isTlsDisableVerification(), true);
        softAssert.assertEquals(grpcRequestConfig.getAwaitTerminationTimeout(), 1000);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMetadataSize(), 1000);
        softAssert.assertEquals(grpcRequestConfig.getMaxInboundMessageSize(), 1000);
        softAssert.assertAll();
    }

    @Test
    public void canCreateConfigObjectWithBuilder() {
        GrpcRequestConfig grpcRequestConfig =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        String grpcRequestConfigString = grpcRequestConfig.toString();
        Assert.assertEquals(
                GrpcRequestConfig.builder().toString(),
                "GrpcRequestConfig.GrpcRequestConfigBuilder(maxInboundMessageSize=0,"
                    + " maxInboundMetadataSize=0, hostPort=null, protoFolder=null, libFolder=null,"
                    + " fullMethod=null, tls=false, tlsDisableVerification=false,"
                    + " awaitTerminationTimeout=0)");
        Assert.assertEquals(
                grpcRequestConfigString,
                "GrpcRequestConfig{maxInboundMessageSize=1000, maxInboundMetadataSize=1000,"
                    + " hostPort='a', testProtoFile='b', libFolder='c', fullMethod='d', tls=true,"
                    + " tlsDisableVerification=true, awaitTerminationTimeout=1000}");
    }

    @Test
    public void canInitGrpcRequestConfig() {
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        String grpcRequestConfigString = grpcRequestConfig.toString();
        Assert.assertNotNull(grpcRequestConfig);
        Assert.assertEquals(
                grpcRequestConfigString,
                "GrpcRequestConfig{maxInboundMessageSize=4194304, maxInboundMetadataSize=8192,"
                    + " hostPort='null', testProtoFile='null', libFolder='null', fullMethod='null',"
                    + " tls=false, tlsDisableVerification=false, awaitTerminationTimeout=5000}");
    }

    @Test
    public void canGetDefaultGrpcRequestConfig() {
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        Assert.assertEquals(grpcRequestConfig.getMaxInboundMessageSize(), 4194304);
        Assert.assertEquals(grpcRequestConfig.getMaxInboundMetadataSize(), 8192);
        Assert.assertEquals(grpcRequestConfig.getAwaitTerminationTimeout(), 5000);
    }

    @Test
    public void canSetConfigWithSetter() {
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        grpcRequestConfig.setHostPort("a");
        grpcRequestConfig.setProtoFolder("b");
        grpcRequestConfig.setLibFolder("c");
        grpcRequestConfig.setFullMethod("d");
        grpcRequestConfig.setTls(true);
        grpcRequestConfig.setTlsDisableVerification(true);
        grpcRequestConfig.setAwaitTerminationTimeout(1000);
        grpcRequestConfig.setMaxInboundMessageSize(1000);
        grpcRequestConfig.setMaxInboundMetadataSize(1000);
        String grpcRequestConfigString = grpcRequestConfig.toString();
        Assert.assertEquals(
                grpcRequestConfigString,
                "GrpcRequestConfig{maxInboundMessageSize=1000, maxInboundMetadataSize=1000,"
                    + " hostPort='a', testProtoFile='b', libFolder='c', fullMethod='d', tls=true,"
                    + " tlsDisableVerification=true, awaitTerminationTimeout=1000}");
        Assert.assertTrue(grpcRequestConfig.hashCode() != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort(null)
                                .protoFolder(null)
                                .libFolder(null)
                                .fullMethod(null)
                                .build()
                                .hashCode()
                        != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort(null)
                                .protoFolder(null)
                                .libFolder(null)
                                .fullMethod(null)
                                .tlsDisableVerification(false)
                                .tls(false)
                                .build()
                                .hashCode()
                        != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort("a")
                                .protoFolder(null)
                                .libFolder(null)
                                .fullMethod(null)
                                .build()
                                .hashCode()
                        != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort(null)
                                .protoFolder("b")
                                .libFolder(null)
                                .fullMethod(null)
                                .build()
                                .hashCode()
                        != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort(null)
                                .protoFolder(null)
                                .libFolder("c")
                                .fullMethod(null)
                                .build()
                                .hashCode()
                        != 0);
        Assert.assertTrue(
                GrpcRequestConfig.builder()
                                .hostPort(null)
                                .protoFolder(null)
                                .libFolder(null)
                                .fullMethod("d")
                                .build()
                                .hashCode()
                        != 0);
    }

    @Test
    public void canPerformObjectMethod() {
        GrpcRequestConfig grpcRequestConfigA =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        GrpcRequestConfig grpcRequestConfigB =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        Assert.assertTrue(grpcRequestConfigA.canEqual(grpcRequestConfigB));
        Assert.assertTrue(grpcRequestConfigA.canEqual(grpcRequestConfigA));
    }

    @Test
    public void canGenerateHashCodeWithDefaultGrpcConfigObject() {
        GrpcRequestConfig grpcRequestConfig = new GrpcRequestConfig();
        Assert.assertTrue(grpcRequestConfig.hashCode() != 0);
        Assert.assertEquals(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .toString(),
                "GrpcRequestConfig.GrpcRequestConfigBuilder(maxInboundMessageSize=1000,"
                        + " maxInboundMetadataSize=1000, hostPort=a, protoFolder=b, libFolder=c,"
                        + " fullMethod=d, tls=true, tlsDisableVerification=true,"
                        + " awaitTerminationTimeout=1000)");
    }

    @Test
    public void canPerformEqual() {
        GrpcRequestConfig grpcRequestConfigA =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        GrpcRequestConfig grpcRequestConfigB =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build();
        GrpcRequestConfig grpcRequestConfigC =
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(3000)
                        .maxInboundMessageSize(3000)
                        .maxInboundMetadataSize(3000)
                        .build();

        Assert.assertTrue(grpcRequestConfigA.equals(grpcRequestConfigA));
        Assert.assertTrue(grpcRequestConfigA.equals(grpcRequestConfigB));
        Assert.assertFalse(grpcRequestConfigA.equals(grpcRequestConfigC));
        Assert.assertFalse(grpcRequestConfigA.equals(null));
        Assert.assertFalse(grpcRequestConfigA.equals(new GrpcResponse()));

        Assert.assertTrue(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder(null)
                        .libFolder(null)
                        .fullMethod(null)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort(null)
                                        .protoFolder(null)
                                        .libFolder(null)
                                        .fullMethod(null)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1001)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1001)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1001)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(false)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(false)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod(null)
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder(null)
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder(null)
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .tls(true)
                        .tlsDisableVerification(true)
                        .awaitTerminationTimeout(1000)
                        .maxInboundMessageSize(1000)
                        .maxInboundMetadataSize(1000)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .tls(true)
                                        .tlsDisableVerification(true)
                                        .awaitTerminationTimeout(1000)
                                        .maxInboundMessageSize(1000)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder(null)
                        .libFolder(null)
                        .fullMethod(null)
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort(null)
                                        .protoFolder(null)
                                        .libFolder(null)
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder(null)
                        .libFolder(null)
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort(null)
                                        .protoFolder(null)
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));
        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder(null)
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort(null)
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort(null)
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));
        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort(null)
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder(null)
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder(null)
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod(null)
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("aa")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("bb")
                                        .libFolder("c")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("cc")
                                        .fullMethod("d")
                                        .maxInboundMetadataSize(1000)
                                        .build()));

        Assert.assertFalse(
                GrpcRequestConfig.builder()
                        .hostPort("a")
                        .protoFolder("b")
                        .libFolder("c")
                        .fullMethod("d")
                        .build()
                        .equals(
                                GrpcRequestConfig.builder()
                                        .hostPort("a")
                                        .protoFolder("b")
                                        .libFolder("c")
                                        .fullMethod("dd")
                                        .maxInboundMetadataSize(1000)
                                        .build()));
    }
}
