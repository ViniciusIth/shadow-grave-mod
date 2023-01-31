package viniciusith.shadowgrave;

import net.fabricmc.api.ClientModInitializer;
import viniciusith.shadowgrave.registry.EntityRegistryClient;

public class ShadowGraveModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.

        EntityRegistryClient.registerRenderers();
    }
}