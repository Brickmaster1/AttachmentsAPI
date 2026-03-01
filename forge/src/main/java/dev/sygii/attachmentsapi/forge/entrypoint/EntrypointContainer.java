/** ATTRIBUTION
 *  This code is derived in part or whole from <a href="https://github.com/TT432/Cardinal-Components-API-Forge/blob/master/src/main/java/io/github/tt432/ccaforge/entrypointes/EntrypointContainer.java</a>
 */
package dev.sygii.attachmentsapi.forge.entrypoint;

import net.minecraftforge.forgespi.locating.IModFile;

/**
 * @param entrypoint entrypoint of the container
 * @param mod        which mod hold the container
 * @author DustW
 */
public record EntrypointContainer<T>(T entrypoint, IModFile mod) {
}