package net.minegate.fr.moreblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class SlabGravelBlock extends SlabBlock
{
    public SlabGravelBlock(Settings settings)
    {
        super(settings);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify)
    {
        world.getBlockTickScheduler().schedule(pos, this, this.getFallDelay());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom)
    {
        world.getBlockTickScheduler().schedule(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)
        {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos).with(TYPE, SlabType.BOTTOM));
            this.configureFallingBlockEntity(fallingBlockEntity);
            world.spawnEntity(fallingBlockEntity);
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity)
    {
    }

    protected int getFallDelay()
    {
        return 2;
    }

    public static boolean canFallThrough(BlockState state)
    {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity)
    {
    }

    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity)
    {
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if (random.nextInt(16) == 0)
        {
            BlockPos blockPos = pos.down();
            if (canFallThrough(world.getBlockState(blockPos)))
            {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() - 0.05D;
                double f = (double) pos.getZ() + random.nextDouble();
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public int getColor(BlockState state, BlockView world, BlockPos pos)
    {
        return -16777216;
    }
}
